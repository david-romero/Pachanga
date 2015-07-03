/**
 * 
 */
package com.p.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p.model.Novedad;
import com.p.model.User;
import com.p.model.repositories.NovedadRepository;

@Service
/**
 * @author David
 *
 */
public class NovedadService {

	private static final int PAGE_SIZE = 5;
	
	@Autowired
	private NovedadRepository repository;

	@Transactional(readOnly=true)
	public Page<Novedad> getNovedades(User usrReceptor,
			Integer pagina) {
		PageRequest page = new PageRequest(pagina-1, PAGE_SIZE,
				new Sort(new Order(Direction.DESC, "fecha")));
		return repository.getNovedades(usrReceptor.getId(), page);
	}

	@Transactional()
	public Novedad create(User usuario, User userEmisor, Novedad novedad) {
		novedad.setEmisor(userEmisor);
		return create(usuario, novedad);
	}
	
	@Transactional()
	public Novedad create(User usuario, Novedad novedad) {
		novedad.setUsuario(usuario);
		novedad.setFecha(new Date(System.currentTimeMillis()));
		novedad.setLikes(0);
		return save(novedad);
	}

	@Transactional()
	public Novedad save(Novedad novedad) {
		return repository.save(novedad);
	}

	@Transactional(readOnly=true)
	public Novedad findOne(Integer idNovedad) {
		return repository.findOne(idNovedad);
	}

}
