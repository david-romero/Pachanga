package com.p.controller.rest;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.google.common.collect.Sets;
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
		Optional<Partido> optionalPartido  = findPartido(idPartido);
		Assert.isTrue(optionalPartido.isPresent());
		
		if ( optionalPartido.isPresent() ){
			p = optionalPartido.get();
		}
		
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
		optionalPartido = aniadirUsuarioAPartido(p, usr);
		
		if ( optionalPartido.isPresent() ){
			response = new ResponseEntity<Partido>(p, HttpStatus.OK);
			p = optionalPartido.get();
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
		}else{
			response = new ResponseEntity<Partido>(p, HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		
		return response;
	}

	/**
	 * @param idPartido
	 * @param p
	 * @return
	 */
	protected Optional<Partido> findPartido(Integer idPartido) {
		try{
			beginTransaction(true);
			Partido p = partidoService.findOne(idPartido);
			Hibernate.initialize(p.getJugadores());
			commitTransaction();
			return Optional.of(p);
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
			return Optional.empty();
		}
	}

	/**
	 * @param p
	 * @param usr
	 * @return
	 */
	protected Optional<Partido> aniadirUsuarioAPartido(Partido p, User usr) {
		try{
			beginTransaction();
			p = partidoService.apuntarse(p,usr);
			commitTransaction();
			return Optional.of(p);
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
			return Optional.empty();
		}
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
				Optional<PropietarioPartido> propietario = gestionarPropietarioPartido(partido);
				bandera = propietario.isPresent();
				if ( propietario.isPresent() ){
					prop = propietario.get();
				}
			}else{
				response = new ResponseEntity<Partido>(partido, HttpStatus.NOT_FOUND);
			}
			if (bandera){
				try{
					beginTransaction();
					partido = partidoService.save(partido);
					prop.getPartidosCreados().add(partido);
					prop = propietarioPartidoService.save(prop);
					if ( prop instanceof User ){
						partido = partidoService.apuntarse(partido,(User) prop);
					}
					commitTransaction();
					response = new ResponseEntity<Partido>(partido, HttpStatus.OK);
				}catch(Exception e){
					log.error(e);
					rollbackTransaction();
					response = new ResponseEntity<Partido>(partido, HttpStatus.FORBIDDEN);
				}
			}else{
				response = new ResponseEntity<Partido>(partido, HttpStatus.NOT_FOUND);
			}
		}

		return response;
	}

	private Optional<PropietarioPartido> gestionarPropietarioPartido(Partido partido) {
		Optional<PropietarioPartido> opt = null;
		try{
			beginTransaction(true);
			PropietarioPartido prop = propietarioPartidoService.findOne(partido.getPropietario().getId());
			validarPropietario(prop);
			Hibernate.initialize(prop.getPartidosCreados());
			partido.setPropietario(prop);
			commitTransaction();
			opt = Optional.of(prop);
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
			opt = Optional.empty();
		}
		return opt;
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
		Assert.isTrue(partido.getFecha().after(new Date(System.currentTimeMillis())));
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
		Optional<Partido> partido = findPartido(idPartido);
		Assert.isTrue(partido.isPresent());
		try {
			beginTransaction();
			p = partido.get();
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
		Set<Partido> partidos = Sets.newHashSet();
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
		List<Partido> result = Lists.newArrayList(partidos);
		Collections.sort(result,new Comparator<Partido>(){

			@Override
			public int compare(Partido arg0, Partido arg1) {
				return arg0.getFecha().compareTo(arg1.getFecha());
			}
			
		});
		if ( result.size() > 5 ){
			result = result.subList(0, 5);
		}
		return result;
	}
	
	@RequestMapping(value = "/eliminarJugador/{idPartido}/{idJugador}", method = RequestMethod.POST)
	public Partido eliminarJugador(
			@PathVariable(value = "idPartido") Integer idPartido,
			@PathVariable(value = "idJugador") Integer idJugador) {
	
		User userSigned = findUserSigned();
		Partido p = null;
		User usrAEliminar = null;
		try{
			beginTransaction(true);
			p = partidoService.findOne(idPartido);
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
		try{
			beginTransaction(true);
			usrAEliminar = userService.findOne(idJugador);
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
		
		try{
			beginTransaction();
			p = partidoService.eliminarJugador(userSigned,usrAEliminar,p);
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}

		return p;
	}
	
	@RequestMapping(value = "/eliminar/{idPartido}",method = RequestMethod.GET, headers = "Accept=application/json")
	public void remove(Model model,@PathVariable(value="idPartido") Integer idPartido) {
		try{
			beginTransaction(true);
			partidoService.remove(idPartido);
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}

	}
	
	
	@RequestMapping(value = "/relacionados", method = RequestMethod.POST)
	public Page<Partido> relacionados(@RequestBody com.p.model.modelAux.Page page) {
		Page<Partido> pageObtained = null;
		if ( !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) ){
			try {
				beginTransaction(true);
				pageObtained = partidoService.findAllRelacionados(page);
				commitTransaction();
			} catch (Exception e) {
				log.error(e);
				rollbackTransaction();
			}
		}
		return pageObtained;
	}
	
	

}
