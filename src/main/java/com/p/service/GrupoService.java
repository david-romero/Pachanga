package com.p.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.p.model.Grupo;
import com.p.model.Mensaje;
import com.p.model.User;
import com.p.model.repositories.GrupoRepository;

@Service
@Transactional()
public class GrupoService {

	@Autowired
	protected GrupoRepository repository;

	@Autowired
	protected MensajeService mensajeService;
	
	@Autowired
	protected UsersService userService;

	@Transactional(readOnly = false)
	public Grupo save(Grupo grupo) {
		if ( grupo.getFechaCreacion() == null ){
			grupo.setFechaCreacion(new Date(System.currentTimeMillis()));
		}
		Assert.notEmpty(grupo.getUsuarios());
		Grupo grp = repository.save(grupo);
		return grp;
	}

	@Transactional(readOnly = true)
	public Grupo findOne(Integer id) {
		return repository.findOne(id);
	}

	@Transactional(readOnly = true)
	public List<Mensaje> findMensajes(Integer idComunidad) {
		Assert.notNull(idComunidad);
		Assert.isTrue(idComunidad > 0);
		return Lists.newArrayList(mensajeService.findAllGrupo(idComunidad));
	}

	public Grupo create() {
		Grupo grp = new Grupo();
		grp.setFechaCreacion(new Date(System.currentTimeMillis()));
		return grp;
	}
	
	@Transactional()
	public Grupo eliminarUsuarioGrupo(Grupo grp, User userSigned) {
		Assert.notNull(grp);
		Assert.notNull(userSigned);
		Assert.isTrue(grp.getUsuarios().contains(userSigned));
		Assert.isTrue(userSigned.getGrupos().contains(grp));
		Assert.isTrue(grp.getUsuarios().remove(userSigned));
		save(grp);
		Assert.isTrue(userSigned.getGrupos().remove(grp));
		userService.save(userSigned);
		return grp;
	}

}
