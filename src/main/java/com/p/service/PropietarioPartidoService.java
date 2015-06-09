package com.p.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.p.model.PropietarioPartido;
import com.p.model.repositories.PropietarioPartidoRepository;

@Service
public class PropietarioPartidoService {

	@Autowired
	protected PropietarioPartidoRepository repository;
	
	public PropietarioPartido findOne(Integer id){
		Assert.notNull(id);
		Assert.isTrue(id > 0);
		return repository.findOne(id);
	}

	public PropietarioPartido save(PropietarioPartido prop) {
		return repository.save(prop);
	}
	
}
