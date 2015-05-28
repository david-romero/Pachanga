package com.p.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Sets;
import com.p.model.Grupo;
import com.p.model.User;

@Controller
@RequestMapping(value = "/grupo")
public class GrupoController {

	@RequestMapping(value = "/create",method = RequestMethod.GET, headers = "Accept=application/json")
	public String create(Model model) {
		Grupo p = new Grupo();
		p.setUsuarios(Sets.newHashSet());
		p.setTitulo("Grupo nuevo");
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		p.setId(0L);
		User usr = new User();
		usr.setEmail(userSigned.getUsername());
		model.addAttribute("userSigned", usr);
		model.addAttribute("grupo", p);
		return "comunidad";
	}
	
	@RequestMapping(value = "/show/{grupoId}",method = RequestMethod.GET, headers = "Accept=application/json")
	public String show(Model model,
			@PathVariable(value = "grupoId") Integer grupoId) {
		Grupo p = new Grupo();
		p.setUsuarios(Sets.newHashSet());
		p.setTitulo("Grupo nuevo");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User usr = new User();
		if ((auth instanceof AnonymousAuthenticationToken)) {
			usr.setEmail("anonymous@anonymous.com");   
		}else{
			org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			usr.setEmail(userSigned.getUsername());
		}
		p.setId(new Long(grupoId));
		
		
		model.addAttribute("userSigned", usr);
		model.addAttribute("grupo", p);
		return "comunidad";
	}
	
}
