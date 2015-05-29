package com.p.controller;

import java.util.Date;
import java.util.List;

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
import com.p.model.Grupo;
import com.p.model.Lugar;
import com.p.model.Partido;
import com.p.model.User;

@Controller
@RequestMapping(value = "/grupo")
public class GrupoController {

	@RequestMapping(value = "/create",method = RequestMethod.GET, headers = "Accept=application/json")
	public String create(Model model) {
		Grupo p = new Grupo();
		p.setTitulo("Grupo nuevo");
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		p.setId(0L);
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
		Grupo grupo = new Grupo();
		
		grupo.setTitulo("Grupo nuevo");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User usr = new User();
		if ((auth instanceof AnonymousAuthenticationToken)) {
			usr.setEmail("anonymous@anonymous.com");   
		}else{
			org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			usr.setEmail(userSigned.getUsername());
		}
		grupo.setId(new Long(grupoId));
		
		grupo.setUsuarios(Sets.newHashSet(usr));
		List<Partido> objetos = Lists.newArrayList();

		for (int i = 0; i < 4; i++) {
			Partido p = new Partido();
			p.setId(new Long(i));
			p.setPrecio(0.7);
			Lugar lg = new Lugar();
			lg.setTitulo("Hytasa");
			p.setLugar(lg);
			p.setFecha(new Date(System.currentTimeMillis()));
			p.setTitulo("Partido semanal");
			List<User> usuarios = Lists.newArrayList();
			User user = new User();
			user.setEmail("bent@test.com");
			user.setId(1L);
			user.setTieneAvatar(false);
			usuarios.add(user);
			user = new User();
			user.setEmail("bent@test.com");
			user.setId(2L);
			user.setTieneAvatar(true);
			usuarios.add(user);
			user = new User();
			user.setEmail("bent@test.com");
			user.setId(3L);
			user.setTieneAvatar(false);
			usuarios.add(user);
			user = new User();
			user.setEmail("test3@test.com");
			user.setId(4L);
			user.setTieneAvatar(true);
			usuarios.add(user);
			user = new User();
			user.setEmail("test4@test.com");
			user.setId(5L);
			user.setTieneAvatar(false);
			usuarios.add(user);
			user = new User();
			user.setEmail("test5@test.com");
			user.setId(6L);
			user.setTieneAvatar(true);
			usuarios.add(user);
			user = new User();
			user.setEmail("test6@test.com");
			user.setId(7L);
			user.setTieneAvatar(false);
			usuarios.add(user);
			user = new User();
			user.setEmail("test7@test.com");
			user.setId(8L);
			user.setTieneAvatar(true);
			usuarios.add(user);
			p.setJugadores(usuarios.subList(0, 3));
			objetos.add(p);
		}

		grupo.setPartidos(Sets.newHashSet(objetos));
		model.addAttribute("userSigned", usr);
		model.addAttribute("grupo", grupo);
		return "comunidad";
	}
	
}
