package com.p.model.repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.p.model.Partido;
import com.p.model.User;
@Repository
public interface PartidoRepository extends JpaRepository<Partido, Integer>{

	@Query("select p from Partido p where p.fecha > CURRENT_DATE and p.publico = true and p.plazasOcupadas < p.plazas")
	public Page<Partido> getAllFuturosPublicosNoLlenos(Pageable page);
	@Query("select p from Partido p where ?1 MEMBER OF p.jugadores order by p.fecha DESC ")
	public Collection<Partido> getAllJugados(User user);
	
}
