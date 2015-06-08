package com.p.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.p.model.Grupo;
import com.p.model.Mensaje;
import com.p.model.repositories.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	protected GrupoRepository repository;
	
	@Autowired
	protected MensajeService mensajeService;
	
	public Grupo save(Grupo grupo){
		Grupo grp = repository.save(grupo);
		return grp;
	}

	public Grupo findOne(Integer id) {
		return repository.findOne(id);
	}

	public List<Mensaje> findMensajes(Integer idComunidad) {
		Assert.notNull(idComunidad);
		Assert.isTrue(idComunidad > 0);
		return Lists.newArrayList(mensajeService.findAllGrupo(idComunidad));
	}
	
}
