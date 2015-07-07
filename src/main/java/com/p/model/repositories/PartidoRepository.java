package com.p.model.repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.p.model.Partido;
import com.p.model.User;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Integer> {

	@Query("select p from Partido p where p.fecha > CURRENT_DATE and p.publico = true and p.plazasOcupadas < p.plazas order by p.fecha ASC")
	public Page<Partido> getAllFuturosPublicosNoLlenos(Pageable page);

	@Query("select p from Partido p where ?1 MEMBER OF p.jugadores order by p.fecha DESC ")
	public Collection<Partido> getAllJugados(User user);

	@Query("select p from Partido p where ?1 MEMBER OF p.jugadores or p.propietario.id = ?2 order by p.fecha DESC ")
	public Page<Partido> getAllVinculados(User user, Integer userId,
			Pageable page);

	@Query("select p from Partido p where p.fecha BETWEEN ?2 AND ?3 and ?1  MEMBER OF p.jugadores")
	public Collection<Partido> getAllApuntadoEnDia(User usuario, Date from,
			Date to);
	
	@Query("select u.imagen from Partido u where u.id = ?1") 
	public byte[] findImage(Integer id);

}
