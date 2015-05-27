package com.p.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.p.model.Partido;
import com.p.model.repositories.PartidoRepository;

@Service
@Transactional
public class PartidoService {

	@Autowired
	public PartidoRepository repository; 
	
	public Collection<Partido> findAll(){
		return repository.findAll();
	}
	
	public Partido findOne(Integer id){
		Assert.notNull(id);
		Assert.isTrue(id > 0);
		return repository.findOne(new Long(id));
	}
	
	public Partido save(Partido partido){
		Assert.notNull(partido);
		if ( partido.getId() == 0L ){
			Assert.isTrue(partido.getFecha().after(new Date(System.currentTimeMillis())));
		}
		return repository.save(partido);
	}
	
	public Partido create(){
		Partido partido = new Partido();
		partido.setId(0L);
		partido.setFecha(new Date(System.currentTimeMillis()) );
		return partido;
	}
	
}
