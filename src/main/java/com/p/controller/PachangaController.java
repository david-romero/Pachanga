package com.p.controller;

import java.util.Date;

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
import com.p.model.Partido;
import com.p.model.User;
import com.p.service.PartidoService;

@Controller
@RequestMapping(value = "/partido")
public class PachangaController {

	@Autowired
	protected PartidoService partidoService;
	
	@RequestMapping(value = "/create",method = RequestMethod.GET, headers = "Accept=application/json")
	public String create(Model model) {
		Partido p = partidoService.create();
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		User usr = new User();
		usr.setEmail(userSigned.getUsername());

		model.addAttribute("userSigned", usr);
		model.addAttribute("partido", p);
		return "partido";
	}
	
	@RequestMapping(value = "/show/{partidoId}",method = RequestMethod.GET, headers = "Accept=application/json")
	public String show(Model model,
			@PathVariable(value = "partidoId") Integer partidoId) {
		Partido p = new Partido();
		p.setJugadores(Lists.newArrayList());
		p.setFecha(new Date(System.currentTimeMillis()));
		p.setTitulo("Partido nuevo");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User usr = new User();
		if ((auth instanceof AnonymousAuthenticationToken)) {
			usr.setEmail("anonymous@anonymous.com");   
		}else{
			org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			usr.setEmail(userSigned.getUsername());
		}
		p.setId(new Long(partidoId));
		
		
		p.setPropietario(usr);
		model.addAttribute("userSigned", usr);
		model.addAttribute("partido", p);
		return "partido";
	}
	
	
	
}
