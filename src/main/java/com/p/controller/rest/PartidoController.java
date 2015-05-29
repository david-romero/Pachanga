package com.p.controller.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.p.model.Lugar;
import com.p.model.Partido;
import com.p.model.User;
import com.p.service.PartidoService;

@RestController
@RequestMapping(value = "/rest/partido")
public class PartidoController {

	static int cursor = 1;
	
	@Autowired
	protected PartidoService partidoService;

	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public List<Partido> inicio() {
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

		return objetos;
	}

	@RequestMapping(value = "/loadMorePartidos", method = RequestMethod.GET)
	public List<Partido> getMorePartidos() {
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

		return objetos;
	}
	
	@RequestMapping(value = "/apuntarse/{idPartido}", method = RequestMethod.POST)
	public Partido apuntarse(@PathVariable(value = "idPartido") Integer idPartido) {
		Partido p = new Partido();
		Long lg = new Long(idPartido);
		p.setId(lg);
		
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		User userConversationSigned = new User();
		userConversationSigned.setEmail(userSigned.getUsername());
		userConversationSigned.setId(999L);
		
		p.setJugadores((List<User>) Lists.newArrayList(userConversationSigned));
		
		return p;
		
		
		
	}
	
	@RequestMapping(value = "/save",method = RequestMethod.POST, headers = "Accept=application/json")
	public Partido save(Model model) {
		Random rd = new Random();
		Partido p = partidoService.create();
		p.setId(new Long(rd.nextInt(150)+1));
		return p;
	}

}
