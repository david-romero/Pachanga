package com.p.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.p.model.Mensaje;
import com.p.model.repositories.MensajeRepository;

@Service
public class MensajeService {

	@Autowired
	protected MensajeRepository repository;
	
	public Collection<Mensaje> findAllGrupo(Integer idComunidad){
		Assert.notNull(idComunidad);
		Assert.isTrue(idComunidad > 0);
		return repository.getMensajesGrupo(idComunidad);
	}

	public Mensaje save(Mensaje mensajeCopia) {
		Assert.notNull(mensajeCopia);
		return repository.save(mensajeCopia);
	}
	
}
