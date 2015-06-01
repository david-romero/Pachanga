package com.p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.p.model.Partido;
import com.p.model.User;
import com.p.service.PartidoService;

@Controller
@RequestMapping(value = "/partido")
@Transactional
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
		Partido p = partidoService.findOne(new Long(partidoId));
		p.setId(new Long(partidoId));
		
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		User usr = new User();
		usr.setEmail(userSigned.getUsername());
		model.addAttribute("userSigned", usr);
		model.addAttribute("partido", p);
		return "partido";
	}
	
	
	
}
