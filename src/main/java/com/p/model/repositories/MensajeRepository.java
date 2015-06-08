package com.p.model.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.p.model.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer>{

	
	@Query("select m from Mensaje m where m.propietario.id = ?1 order by fecha ASC ")
	public Collection<Mensaje> getMensajesGrupo(Integer grupoId);
	
}
