package com.p.model.repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.p.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer>{

	
	@Query("select m from Notificacion m where m.propietario.id = ?1 order by fecha ASC ")
	public Collection<Notificacion> getMensajesGrupo(Integer grupoId);
	@Query("select m from Notificacion m where m.receptor.id = ?1 or m.emisor.id = ?1 and m.leido = false order by fecha ASC ")
	public Collection<? extends Notificacion> findUltimasNoLeidas(Integer id);
	@Query("select m from Notificacion m where m.receptor.id = ?1 or m.emisor.id = ?1  order by fecha ASC ")
	public Page<Notificacion> findAll(Integer id,Pageable pageable);
	@Query("select m from Notificacion m where m.leido = false and  (m.receptor.id = ?1 or m.emisor.id = ?1) order by fecha ASC ")
	public Collection<? extends Notificacion> findNoLeidas(Integer idUsuario);
	@Query("select m from Notificacion m where m.leido = true and ( m.receptor.id = ?1 or m.emisor.id = ?1 )  order by fecha ASC ")
	public Collection<? extends Notificacion> findLeidas(Integer idUsuario);

	
}
