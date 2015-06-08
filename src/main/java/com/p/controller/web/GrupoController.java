package com.p.controller.web;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.p.controller.AbstractController;
import com.p.model.Grupo;
import com.p.model.Lugar;
import com.p.model.Partido;
import com.p.model.User;
import com.p.service.GrupoService;
import com.p.service.UsersService;

@Controller
@RequestMapping(value = "/grupo")
public class GrupoController extends AbstractController{

	@Autowired
	protected UsersService userService;
	
	@Autowired
	protected GrupoService service;
	
	protected static final Logger log = Logger.getLogger(GrupoController.class);
	
	@RequestMapping(value = "/create",method = RequestMethod.GET, headers = "Accept=application/json")
	public String create(Model model) {
		Grupo p = new Grupo();
		p.setTitulo("Grupo nuevo");
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		p.setId(0);
		User usr = new User();
		usr.setEmail(userSigned.getUsername());
		p.setUsuarios(Sets.newHashSet(usr));
		model.addAttribute("userSigned", usr);
		model.addAttribute("grupo", p);
		return "comunidad";
	}
	
	@RequestMapping(value = "/show/{grupoId}",method = RequestMethod.GET, headers = "Accept=application/json")
	public String show(Model model,
			@PathVariable(value = "grupoId") Integer grupoId) {
		Grupo grupo = null;
		
		
		User usr = findUser();
		
		try{
			beginTransaction(true);
			grupo = service.findOne(grupoId);
			Hibernate.initialize(grupo.getUsuarios());
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
		
		model.addAttribute("userSigned", usr);
		model.addAttribute("grupo", grupo);
		return "comunidad";
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
			rollbackTransaction();
		}
		return usr;
	}
	
}
