package com.p.model.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.p.model.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer>{

	
	@Query("select m from Mensaje m where m.propietario.id = ?1 and TYPE(m) != Notificacion order by fecha ASC ")
	public Collection<Mensaje> getMensajesGrupo(Integer grupoId);
	@Query("select m from Mensaje m where (m.emisor.id = ?1 and m.receptor.id = ?2 ) or"
			+ " (m.emisor.id = ?2 and m.receptor.id = ?1) and TYPE(m) != Notificacion and m.propietario IS NULL order by fecha ASC ")
	public Collection<Mensaje> findConversacion(Integer idEmisor, Integer idReceptor);
	@Query("select m from Mensaje m where m.receptor.id = ?1  and"
			+ " m.leido = false and TYPE(m) != Notificacion order by fecha ASC ")
	public Collection<? extends Mensaje> findMensajesSinLeer(Integer id);
	@Query("select m from Mensaje m where (m.emisor.id = ?1 and m.receptor.id = ?2 ) or"
			+ " (m.emisor.id = ?2 and m.receptor.id = ?1) and m.leido = false and TYPE(m) != Notificacion order by fecha ASC ")
	public Collection<? extends Mensaje> findMensajesSinLeer(Integer idEmisor, Integer idReceptor);
	
}
