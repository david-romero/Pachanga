package com.p.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p.model.Comentario;
import com.p.model.Novedad;
import com.p.model.User;
import com.p.model.repositories.ComentarioRepository;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository repository;
	
	@Transactional
	public Comentario save(Comentario comentario){
		return repository.save(comentario);
	}

	public Comentario save(Comentario comentario, Novedad novedad, User usrEmisor) {
		comentario.setFecha(new Date(System.currentTimeMillis()));
		comentario.setNovedad(novedad);
		comentario.setEmisor(usrEmisor);
		return save(comentario);
	}
	
}
