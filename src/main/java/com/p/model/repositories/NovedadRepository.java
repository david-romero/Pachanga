package com.p.model.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.p.model.Novedad;
@Repository
public interface NovedadRepository extends JpaRepository<Novedad, Integer>{

	@Query("select n from Novedad n where usuario.id = ?1")
	public Page<Novedad> getNovedades(Integer idUsuario,Pageable request);
	
}
