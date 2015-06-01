package com.p.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.p.model.Partido;
import com.p.model.User;
import com.p.model.repositories.PartidoRepository;

@Service
@Transactional
public class PartidoService {

	@Autowired
	protected PartidoRepository repository;

	private static final int PAGE_SIZE = 4;

	public Partido create() {
		Partido p = new Partido();
		p.setJugadores(Lists.newArrayList());
		p.setFecha(new Date(System.currentTimeMillis()));
		p.setPrecio(0.0);
		p.setPlazas(0);
		p.setId(0L);
		p.setTitulo("Partido nuevo");
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		User usr = new User();
		usr.setEmail(userSigned.getUsername());
		p.setPropietario(usr);
		return p;
	}

	public Partido findOne(Long id) {
		Assert.notNull(id);
		Assert.isTrue(id > 0);
		return repository.findOne(id);
	}

	public Collection<? extends Partido> findAll() {
		return repository.findAll();
	}
	@Transactional
	public Page<Partido> findAll(Integer page) {
		PageRequest request = new PageRequest(page - 1, PAGE_SIZE,
				Sort.Direction.DESC, "fecha");
		Page<Partido> pagina = repository.findAll(request);
		pagina.getContent().stream().filter(partido->{
			for ( User usr : partido.getJugadores() ){
				Assert.notNull(usr);
			}
			return true;
		});
		return pagina;
	}

}
