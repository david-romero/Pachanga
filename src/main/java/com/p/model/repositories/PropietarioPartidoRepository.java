package com.p.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p.model.PropietarioPartido;
@Repository
public interface PropietarioPartidoRepository extends JpaRepository<PropietarioPartido, Integer>{

	
	
}
