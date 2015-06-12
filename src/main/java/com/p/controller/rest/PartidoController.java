package com.p.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.p.controller.AbstractController;
import com.p.model.Categoria;
import com.p.model.Grupo;
import com.p.model.Partido;
import com.p.model.PropietarioPartido;
import com.p.model.User;
import com.p.service.CategoriaService;
import com.p.service.NotificacionService;
import com.p.service.PartidoService;
import com.p.service.PropietarioPartidoService;
import com.p.service.UsersService;
import com.p.util.Pair;

@RestController
@RequestMapping(value = "/rest/partido")
@Transactional
public class PartidoController extends AbstractController{

	static int cursor = 1;
	
	@Autowired
	protected PartidoService partidoService;
	
	@Autowired
	protected PropietarioPartidoService propietarioPartidoService;
	
	@Autowired
	protected CategoriaService categoriaService;
	
	@Autowired
	protected NotificacionService notificacionService;
	
	private final static Logger log = Logger.getLogger(PartidoController.class);
	
	@Autowired
	protected UsersService userService;

	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public List<Partido> inicio() {
		List<Partido> partidos = Lists.newArrayList();
		try{
			beginTransaction(true);
			partidos.addAll(partidoService.findAllPosiblesPublicos(1).getContent());
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}

		return partidos;
	}

	@RequestMapping(value = "/loadMorePartidos/{pagina}", method = RequestMethod.GET)
	public List<Partido> getMorePartidos(@PathVariable(value = "pagina") Integer pagina) {
		List<Partido> partidos = Lists.newArrayList();
		Assert.isTrue(pagina > 1);
		partidos.addAll(partidoService.findAllPosiblesPublicos(pagina).getContent());

		return partidos;
	}
	
	@RequestMapping(value = "/apuntarse/{idPartido}", method = RequestMethod.POST)
	public ResponseEntity<Partido> apuntarse(@PathVariable(value = "idPartido") Integer idPartido) {
		Assert.notNull(idPartido);
		Assert.isTrue(idPartido>0);
		ResponseEntity<Partido> response = null;
		Partido p = null;
		try{
			beginTransaction(true);
			p = partidoService.findOne(idPartido);
			Hibernate.initialize(p.getJugadores());
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
			response = new ResponseEntity<Partido>(p, HttpStatus.BAD_REQUEST);
		}
		Assert.notNull(p);
		
		
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		User usr = null;
		try{
			beginTransaction(true);
			usr = userService.getByEmail(userSigned.getUsername());
			Hibernate.initialize(usr.getPartidosJugados());
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
			response = new ResponseEntity<Partido>(p, HttpStatus.FORBIDDEN);
		}
		Assert.notNull(usr);
		try{
			beginTransaction();
			p = partidoService.apuntarse(p,usr);
			commitTransaction();
			response = new ResponseEntity<Partido>(p, HttpStatus.OK);
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
			response = new ResponseEntity<Partido>(p, HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		
		if ( response.getStatusCode().equals(HttpStatus.OK) ){
			//Si Todo ha ido bien debemos enviar una notificacion al creador y a los otros jugadores 
			try {
				beginTransaction();
				notificacionService.usuarioApuntado(p,usr);
				commitTransaction();
			} catch (Exception e) {
				log.error(e);
				rollbackTransaction();
				//Si no se ha podido enviar la notificacion, no devuelvo una respuesta mala
			}
		}
		
		return response;
		
		
		
	}
	
	@RequestMapping(value = "/save",method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Partido> save(Model model,@RequestBody @Valid Partido partido, BindingResult result) {
		ResponseEntity<Partido> response = null;
		PropietarioPartido prop = null;
		boolean bandera = true;
		if ( result.hasErrors() ){
			response = new ResponseEntity<Partido>(partido, HttpStatus.BAD_REQUEST);
		}else{
			validarPartido(partido);
			bandera = gestionarCategoriaPartido(partido);
			if (bandera){
				Pair<PropietarioPartido,Boolean> pair = gestionarPropietarioPartido(partido);
				bandera = pair.getSecond();
				prop = pair.getFirst();
			}else{
				response = new ResponseEntity<Partido>(partido, HttpStatus.NOT_FOUND);
			}
			if (bandera){
				try{
					beginTransaction();
					partido = partidoService.save(partido);
					commitTransaction();
				}catch(Exception e){
					log.error(e);
					rollbackTransaction();
					response = new ResponseEntity<Partido>(partido, HttpStatus.FORBIDDEN);
				}
				try{
					beginTransaction();
					prop.getPartidosCreados().add(partido);
					prop = propietarioPartidoService.save(prop);
					commitTransaction();
					
				}catch(Exception e){
					log.error(e);
					rollbackTransaction();
					response = new ResponseEntity<Partido>(partido, HttpStatus.FORBIDDEN);
				}
				ResponseEntity<Partido> respApuntarse = apuntarse(partido.getId());
				if ( respApuntarse.getStatusCode().equals(HttpStatus.OK) ){
					response = new ResponseEntity<Partido>(partido, HttpStatus.CREATED);
				}else{
					response = new ResponseEntity<Partido>(partido, HttpStatus.FORBIDDEN);
				}
			}else{
				response = new ResponseEntity<Partido>(partido, HttpStatus.NOT_FOUND);
			}
		}

		return response;
	}

	private Pair<PropietarioPartido,Boolean> gestionarPropietarioPartido(Partido partido) {
		boolean bandera = true;
		PropietarioPartido prop = null;
		
		try{
			beginTransaction(true);
			prop = propietarioPartidoService.findOne(partido.getPropietario().getId());
			validarPropietario(prop);
			Hibernate.initialize(prop.getPartidosCreados());
			partido.setPropietario(prop);
			commitTransaction();
		}catch(Exception e){
			bandera = false;
			log.error(e);
			rollbackTransaction();
		}
		Pair<PropietarioPartido,Boolean> result = Pair.create(prop, bandera);
		return result;
	}

	private void validarPropietario(PropietarioPartido prop) {
		User usr = findUserSigned();
		if ( prop instanceof User ){
			Assert.isTrue(usr.getId().equals(prop.getId()));
		}else if ( prop instanceof Grupo ){
			Assert.isTrue( ( (Grupo) prop).getUsuarios().contains(usr)  );
		}
	}

	private void validarPartido(Partido partido) {
		Assert.notNull(partido);
		Assert.notNull(partido.getCategoriaTitulo());
		Assert.isTrue(partido.getCategoriaTitulo().length() > 0);
		Assert.notNull(partido.getPropietario());
		Assert.notNull(partido.getPropietario().getId());
		Assert.isTrue(partido.getPropietario().getId()>0);
	}

	private boolean gestionarCategoriaPartido(Partido partido) {
		boolean bandera = true;
		try{
			beginTransaction();
			Categoria c = categoriaService.findByTitulo(partido.getCategoriaTitulo());
			partido.setCategoria(c);
			commitTransaction();
		}catch(Exception e){
			bandera = false;
			log.error(e);
			rollbackTransaction();
			
		}
		return bandera;
	}
	
	
	@RequestMapping(value = "/editImage/{idPartido}",method = RequestMethod.POST, headers = "Accept=application/json")
	public Partido save(Model model,@RequestParam("foto") MultipartFile file,
			@PathVariable(value = "idPartido") Integer idPartido) {
		Partido p = null;
		try {
			beginTransaction();
			p = partidoService.findOne(idPartido);
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			e.printStackTrace();
		}
		Assert.notNull(p);
		try {
			beginTransaction();
			p.setImagen(file.getBytes());
			p = partidoService.save(p);
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			e.printStackTrace();
		}
		return p;
	}
	
	@RequestMapping(value = "/jugados", method = RequestMethod.GET)
	public List<Partido> jugados() {
		List<Partido> partidos = Lists.newArrayList();
		if ( !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) ){
			User usr = null;
			org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			try {
				beginTransaction(true);
				usr = userService.getByEmail(userSigned.getUsername());
				Hibernate.initialize(usr.getPartidosJugados());
				Hibernate.initialize(usr.getPartidosCreados());
				commitTransaction();
			} catch (Exception e) {
				log.error(e);
				rollbackTransaction();
			}
			partidos.addAll(usr.getPartidosJugados());
			partidos.addAll(usr.getPartidosCreados());
		}
		partidos = partidos.subList(0, partidos.size() > 5 ? 5 : partidos.size());
		return partidos;
	}
	
	@RequestMapping(value = "/eliminarJugador/{idPartido}/{idJugador}", method = RequestMethod.POST)
	public Partido eliminarJugador(
			@PathVariable(value = "idPartido") Integer idPartido,
			@PathVariable(value = "idJugador") Integer idJugador) {
		//TODO

		return new Partido();
	}
	
	

}
