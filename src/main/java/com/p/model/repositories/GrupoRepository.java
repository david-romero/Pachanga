package com.p.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p.model.Grupo;
@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer>{

	
	
}
