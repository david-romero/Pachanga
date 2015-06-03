package com.p.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.model.Grupo;
import com.p.model.repositories.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	protected GrupoRepository repository;
	
	public Grupo save(Grupo grupo){
		Grupo grp = repository.save(grupo);
		return grp;
	}

	public Grupo findOne(Integer id) {
		return repository.findOne(id);
	}
	
}
