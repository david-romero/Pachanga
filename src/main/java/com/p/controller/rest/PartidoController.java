package com.p.controller.rest;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.p.controller.AbstractController;
import com.p.model.Partido;
import com.p.model.User;
import com.p.service.PartidoService;
import com.p.service.UsersService;

@RestController
@RequestMapping(value = "/rest/partido")
@Transactional
public class PartidoController extends AbstractController{

	static int cursor = 1;
	
	@Autowired
	protected PartidoService partidoService;
	
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
		partidos.addAll(partidoService.findAll(pagina).getContent());

		return partidos;
	}
	
	@RequestMapping(value = "/apuntarse/{idPartido}", method = RequestMethod.POST)
	public Partido apuntarse(@PathVariable(value = "idPartido") Integer idPartido) {
		Partido p = new Partido();
		Long lg = new Long(idPartido);
		p.setId(idPartido);
		
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		User userConversationSigned = new User();
		userConversationSigned.setEmail(userSigned.getUsername());
		userConversationSigned.setId(999);
		
		p.setJugadores((List<User>) Lists.newArrayList(userConversationSigned));
		
		return p;
		
		
		
	}
	
	@RequestMapping(value = "/save",method = RequestMethod.POST, headers = "Accept=application/json")
	public Partido save(Model model,@RequestBody Partido partido) {
		Random rd = new Random();
		Partido p = partidoService.create();
		p.setId(rd.nextInt(150)+1);
		return p;
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
			User userConversationSigned = findUserSigned();
			try{
				beginTransaction();
				partidos.addAll(partidoService.findAllJugados(userConversationSigned));
				commitTransaction();
			}catch(Exception e){
				e.printStackTrace();
				rollbackTransaction();
			}
		}

		return partidos;
	}
	
	@RequestMapping(value = "/eliminarJugador/{idPartido}/{idJugador}", method = RequestMethod.POST)
	public Partido eliminarJugador(
			@PathVariable(value = "idPartido") Integer idPartido,
			@PathVariable(value = "idJugador") Integer idJugador) {
		//TODO

		return new Partido();
	}
	
	private User findUserSigned() {
		User usr = null;
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		try {
			beginTransaction(true);
			usr = userService.getByEmail(userSigned.getUsername());
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		return usr;
	}

}
