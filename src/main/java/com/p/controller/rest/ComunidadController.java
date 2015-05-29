package com.p.controller.rest;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.p.model.Lugar;
import com.p.model.Partido;
import com.p.model.User;

@RestController
@RequestMapping(value = "/rest/comunidad")
public class ComunidadController {

	@RequestMapping(value = "/partidos/{idComunidad}", method = RequestMethod.GET)
	public List<Partido> inicio(@PathVariable(value = "idComunidad") Integer idComunidad) {
		List<Partido> objetos = Lists.newArrayList();

		for (int i = 0; i < 8; i++) {
			Partido p = new Partido();
			p.setId(new Long(i));
			p.setPrecio(0.7);
			Lugar lg = new Lugar();
			lg.setTitulo("Hytasa");
			p.setLugar(lg);
			p.setPlazas(5);
			p.setFecha(new Date(System.currentTimeMillis()));
			p.setTitulo("Partido semanal");
			List<User> usuarios = Lists.newArrayList();
			User user = new User();
			if ( i > 5){
				user.setEmail("bent@test.com");
				user.setId(1L);
				user.setTieneAvatar(false);
				usuarios.add(user);
			}
			if ( i == 1 ){
				p.setFecha(new Date(System.currentTimeMillis()+1534465));
			}
			if ( i == 2 || i == 4 ){
				p.setPlazas(3);
			}
			user = new User();
			user.setEmail("bent1@test.com");
			user.setId(2L);
			user.setTieneAvatar(true);
			usuarios.add(user);
			user = new User();
			user.setEmail("bent2@test.com");
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
	
}
