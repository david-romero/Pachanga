package com.p.service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.p.model.Notificacion;
import com.p.model.Partido;
import com.p.model.User;
import com.p.model.repositories.NotificacionRepository;
import com.p.util.Pair;

@Service
@Transactional(isolation=Isolation.READ_UNCOMMITTED)
public class NotificacionService {
	
	private static final int PAGE_SIZE = 10;
	@Autowired
	protected UsersService userService;
	@Autowired
	protected NotificacionRepository repository;
	
	@Autowired
	protected EntityManagerFactory emf;

	public Notificacion create(){
		Notificacion notificacion = new Notificacion();
		notificacion.setFecha(new Date(System.currentTimeMillis()));
		notificacion.setLeido(false);
		return notificacion;
	}
	@Transactional
	public void usuarioApuntado(Partido p, User usr) {
		Notificacion notificacion = create();
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
		//Primero enviamos una notificacion al propietario del partido en caso de que no sea un grupo
		if ( p.getPropietario() instanceof User ){
			
			notificacion.setReceptor((User) p.getPropietario());
			notificacion.setTitulo("El usuario " + usr.getEmail() + " se ha apuntado a una de tus pachangas");
			notificacion.setContenido("El usuario " + usr.getEmail() + " se ha apuntado a "
					+ "la pachanga creada por ti con t&iacute;tulo " + p.getTitulo() + 
					"  para el día  " + sdf1.format(p.getFecha()) + " a la hora " + sdf3.format(p.getFecha()) +
							". Ya solo quedan " + ( p.getPlazas() - p.getPlazasOcupadas() ) + " plazas!");
			notificacion.setEmisor(usr);
			save(notificacion);
		}
	}
	@Transactional
	public Notificacion save(Notificacion notificacion) {
		return repository.save(notificacion);
	}
	@Transactional
	public Collection<? extends Notificacion> findUltimasNoLeidas(User usr, Optional<Integer> limit) {
		if ( limit.isPresent() ){
			EntityManager em = emf.createEntityManager();
			List<Notificacion> list = em.createQuery("select m from Notificacion m where m.receptor.id = :idUsuario and m.leido = false order by fecha ASC")
					.setMaxResults(limit.get())
					.setParameter("idUsuario", usr.getId())
					.getResultList();
			return list;
		}else{
			return repository.findUltimasNoLeidas(usr.getId());
		}
	}
	@Transactional
	public Pair<Collection<? extends Notificacion>,Integer> findAll(User usr,Integer pagina) {
		PageRequest request = new PageRequest(pagina - 1, PAGE_SIZE,
				Sort.Direction.ASC, "fecha");
		Page<Notificacion> page =  repository.findAll(usr.getId(),request);
		return Pair.create(page.getContent(), page.getTotalPages());
	}
	@Transactional
	public void remove(Notificacion notificacion) {
		Assert.notNull(notificacion);
		Assert.notNull(notificacion.getId());
		Assert.isTrue(notificacion.getId() > 0);
		repository.delete(notificacion);
	}
	
	@Transactional(readOnly=true)
	public Collection<? extends Notificacion> findLeidas(User usr) {
		return repository.findNoLeidas(usr.getId());
	}

}
