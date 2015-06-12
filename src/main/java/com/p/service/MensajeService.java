package com.p.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.p.model.Mensaje;
import com.p.model.User;
import com.p.model.repositories.MensajeRepository;

@Service
public class MensajeService {

	@Autowired
	protected MensajeRepository repository;
	
	@Transactional(readOnly=true)
	public Collection<Mensaje> findAllGrupo(Integer idComunidad){
		Assert.notNull(idComunidad);
		Assert.isTrue(idComunidad > 0);
		return repository.getMensajesGrupo(idComunidad);
	}
	
	@Transactional(readOnly=false)
	public Mensaje save(Mensaje mensajeCopia) {
		Assert.notNull(mensajeCopia);
		return repository.save(mensajeCopia);
	}

	@Transactional(readOnly=true)
	public Collection<Mensaje> findConversacion(User emisor,
			User receptor) {
		Assert.notNull(emisor);
		Assert.notNull(receptor);
		Assert.notNull(emisor.getId());
		Assert.notNull(receptor.getId());
		return repository.findConversacion(emisor.getId(),receptor.getId());
	}

	@Transactional(readOnly=true)
	public Collection<? extends Mensaje> findMensajesSinLeer(User emisor) {
		Assert.notNull(emisor);
		Assert.notNull(emisor.getId());
		return repository.findMensajesSinLeer(emisor.getId());
	}
	@Transactional(readOnly=true)
	public Collection<? extends Mensaje> findMensajesSinLeer(User emisor, User receptor) {
		Assert.notNull(emisor);
		Assert.notNull(receptor);
		Assert.notNull(emisor.getId());
		Assert.notNull(receptor.getId());
		return repository.findMensajesSinLeer(emisor.getId(),receptor.getId());
	}
	
}
