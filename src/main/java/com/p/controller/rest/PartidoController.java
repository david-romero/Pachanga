package com.p.controller.rest;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.p.model.Lugar;
import com.p.model.Mensaje;
import com.p.model.Partido;
import com.p.model.User;

@RestController
@RequestMapping(value = "/rest/partido")
public class PartidoController {

	static int cursor = 1;

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
			Set<User> usuarios = Sets.newHashSet();
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
			p.setJugadores(usuarios);
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
			Set<User> usuarios = Sets.newHashSet();
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


			p.setJugadores(usuarios);
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
		
		p.setJugadores((Set<User>) Sets.newHashSet(userConversationSigned));
		
		return p;
		
		
		
	}
	

	@RequestMapping(value = "/jugados", method = RequestMethod.GET)
	public List<Partido> partidosJugados() {
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
			Set<User> usuarios = Sets.newHashSet();
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
			p.setJugadores(usuarios);
			objetos.add(p);
		}

		return objetos;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Partido save(Model model,
			@RequestBody Partido partido) {
		return partido;
	}

}
