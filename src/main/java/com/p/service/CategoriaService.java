package com.p.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.model.Categoria;
import com.p.model.repositories.CategoriaRepository;

@Service
@Transactional
public class CategoriaService {
	
	@Autowired
	protected CategoriaRepository repository;
	
	public Categoria findByTitulo(String titulo){
		return repository.findByTitulo(titulo);
	}
	
}
