package com.p.controller.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.p.controller.AbstractController;
import com.p.model.Partido;
import com.p.model.PropietarioPartido;
import com.p.model.User;
import com.p.service.GrupoService;
import com.p.service.PartidoService;
import com.p.service.UsersService;

@Controller
@Transactional()
@RequestMapping(value = "/partido")
public class PachangaController extends AbstractController{

	@Autowired
	protected PartidoService partidoService;
	@Autowired
	protected UsersService userService;
	@Autowired
	protected GrupoService grupoService;
	
	private final static Logger log = Logger.getLogger(PachangaController.class);
	
	@RequestMapping(value = "/create",method = RequestMethod.GET, headers = "Accept=application/json")
	public String create(Model model,@RequestParam(value="propietario",required=false) Integer propietarioId) {
		PropietarioPartido propietario = null;
		User userSigned = findUser();
		if ( propietarioId == null ){
			propietario = userSigned;
		}else{
			try{
				beginTransaction(true);
				propietario = grupoService.findOne(propietarioId);
				commitTransaction();
			}catch(Exception e){
				log.error(e);
				rollbackTransaction();
			}
		}
		Partido p = partidoService.create();
		p.setPropietario(propietario);

		model.addAttribute("userSigned", userSigned);
		model.addAttribute("partido", p);
		return "partido";
	}
	
	@RequestMapping(value = "/remove/{idPartido}",method = RequestMethod.GET, headers = "Accept=application/json")
	public String remove(Model model,@PathVariable(value="idPartido") Integer idPartido) {
		try{
			beginTransaction(true);
			partidoService.remove(idPartido);
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}

		return "redirect:/partido/all";
	}
	
	@RequestMapping(value = "/show/{partidoId}",method = RequestMethod.GET, headers = "Accept=application/json")
	public String show(Model model,
			@PathVariable(value = "partidoId") Integer partidoId) {
		Partido p = null;
		try{
			beginTransaction(true);
			p = partidoService.findOne(partidoId);
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
		
		User usr = new User();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			usr = findUser();
		}else{
			usr.setEmail("anonymous@anonymous.com");
		}
		
		model.addAttribute("userSigned", usr);
		model.addAttribute("partido", p);
		return "partido";
	}
	
	@RequestMapping(value = "/all",method = RequestMethod.GET, headers = "Accept=application/json")
	public String showAll(Model model) {
		Set<Partido> partidos = Sets.newHashSet();
		User usr = null;
		if ( !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) ){
			
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
		
		model.addAttribute("userSigned", usr);
		model.addAttribute("partidos", partidos);
		return "partidos";
	}
	
	@RequestMapping(value = "/calendario",method = RequestMethod.GET, headers = "Accept=application/json")
	public String calendario(Model model) {
		Set<Partido> partidos = Sets.newHashSet();
		User usr = null;
		if ( !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) ){
			
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
		
		model.addAttribute("userSigned", usr);
		model.addAttribute("partidos", partidos);
		return "calendario";
	}
	
	private User findUser() {
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
