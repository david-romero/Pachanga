package com.p.model.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.p.model.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Integer>{

	@Query("select v from Voto v where v.receptor.id = ?1")
	public Collection<Voto> getAllVotesRecibidos(Integer userId);
	
	@Query("select v from Voto v where v.receptor.id = ?1 and v.emisor.id = ?2")
	public Voto getVotoBetween(Integer receptorId,Integer emisorId);
	
}
