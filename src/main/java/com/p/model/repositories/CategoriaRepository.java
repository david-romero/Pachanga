package com.p.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.p.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

	@Query("select c from Categoria c where c.titulo = ?1")
	public Categoria findByTitulo(String titulo);
	
}
