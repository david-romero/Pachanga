package com.p.controller.web;

import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Sets;
import com.p.controller.AbstractController;
import com.p.model.Grupo;
import com.p.model.User;
import com.p.service.GrupoService;
import com.p.service.UsersService;

@Controller
@RequestMapping(value = "/grupo")
@Transactional
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
		
		
		User usr = findUserSigned();
		Integer size = 0;
		Set<User> usuariosGrupo = Sets.newHashSet();
		try{
			beginTransaction(true);
			grupo = service.findOne(grupoId);
			Hibernate.initialize(grupo.getUsuarios());
			usuariosGrupo.addAll(grupo.getUsuarios());
			size = usuariosGrupo.size();
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			rollbackTransaction();
		}
		
		Assert.isTrue(usuariosGrupo.contains(usr));
		
		model.addAttribute("userSigned", usr);
		model.addAttribute("grupo", grupo);
		model.addAttribute("grupoSize", size);
		model.addAttribute("usuariosGrupo", usuariosGrupo);
		return "comunidad";
	}

	@ExceptionHandler(IllegalArgumentException.class)
	/**
	 * panic
	 * @author David Romero Alcaide
	 * @param oops
	 * @return
	 */
	public ModelAndView panic(IllegalArgumentException oops) {
		log.error(oops);
		ModelAndView result;

		result = new ModelAndView("error");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exceptionMessage", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}
	
}
