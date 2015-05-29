package com.p.service;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.p.model.Partido;
import com.p.model.User;

@Service
public class PartidoService {

	public Partido create(){
		Partido p = new Partido();
		p.setJugadores(Lists.newArrayList());
		p.setFecha(new Date(System.currentTimeMillis()));
		p.setPrecio(0.0);
		p.setPlazas(0);
		p.setId(0L);
		p.setTitulo("Partido nuevo");
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		User usr = new User();
		usr.setEmail(userSigned.getUsername());
		p.setPropietario(usr);
		return p;
	}
	
}
