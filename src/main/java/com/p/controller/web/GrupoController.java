package com.p.controller.web;

import java.util.Date;
import java.util.List;

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
import com.p.service.UsersService;

@Controller
@RequestMapping(value = "/grupo")
public class GrupoController extends AbstractController{

	@Autowired
	protected UsersService userService;
	
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
		Grupo grupo = new Grupo();
		
		grupo.setTitulo("Grupo nuevo");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User usr = new User();
		if ((auth instanceof AnonymousAuthenticationToken)) {
			usr.setEmail("anonymous@anonymous.com");   
		}else{
			usr = findUser();
		}
		grupo.setId(grupoId);
		
		grupo.setUsuarios(Sets.newHashSet(usr));
		List<Partido> objetos = Lists.newArrayList();

		for (int i = 0; i < 4; i++) {
			Partido p = new Partido();
			p.setId(i);
			p.setPrecio(0.7);
			Lugar lg = new Lugar();
			lg.setTitulo("Hytasa");
			p.setLugar(lg);
			p.setFecha(new Date(System.currentTimeMillis()));
			p.setTitulo("Partido semanal");
			List<User> usuarios = Lists.newArrayList();
			User user = new User();
			user.setEmail("bent@test.com");
			user.setId(1);
			user.setTieneAvatar(false);
			usuarios.add(user);
			user = new User();
			user.setEmail("bent@test.com");
			user.setId(2);
			user.setTieneAvatar(true);
			usuarios.add(user);
			user = new User();
			user.setEmail("bent@test.com");
			user.setId(3);
			user.setTieneAvatar(false);
			usuarios.add(user);
			user = new User();
			user.setEmail("test3@test.com");
			user.setId(4);
			user.setTieneAvatar(true);
			usuarios.add(user);
			user = new User();
			user.setEmail("test4@test.com");
			user.setId(5);
			user.setTieneAvatar(false);
			usuarios.add(user);
			user = new User();
			user.setEmail("test5@test.com");
			user.setId(6);
			user.setTieneAvatar(true);
			usuarios.add(user);
			user = new User();
			user.setEmail("test6@test.com");
			user.setId(7);
			user.setTieneAvatar(false);
			usuarios.add(user);
			user = new User();
			user.setEmail("test7@test.com");
			user.setId(8);
			user.setTieneAvatar(true);
			usuarios.add(user);
			p.setJugadores(usuarios.subList(0, 3));
			objetos.add(p);
		}

		grupo.setPartidosCreados(Sets.newHashSet(objetos));
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
