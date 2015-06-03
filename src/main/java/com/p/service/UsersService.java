package com.p.service;

import java.util.Collection;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.p.model.User;
import com.p.model.repositories.UserRepository;

@Service("usersService")
@Transactional
public class UsersService {

	protected static Logger logger = Logger.getLogger("service");

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	@Autowired
	private UserRepository repository;

	@Transactional
	/**
	 * Borra un usuario según sea usuari de la web (su id empieza por 1) o usuario de llavero(su id empieza por 0)
	 * 
	 * @param id
	 *            el id del usuario existente
	 */
	public void delete(Integer id) {
		Assert.notNull(id);
		Assert.isTrue(id > 0);
		repository.delete(id);
	}


	/**
	 * Guarda o edita sengún si el ID esta o no relleno
	 * 
	 * @param us
	 */
	@Transactional()
	public User save(User us) {
		User usr = repository.save(us);
		return usr;
	}
	
	
	@Transactional
	public User getByEmail(String login) {
		Assert.notNull(login);
		Assert.isTrue(login.length() > 0);
		return repository.findByEmail(login);
	}
	@Transactional
	public User findOne(Integer id) {
		Assert.notNull(id);
		Assert.isTrue(id > 0L);
		return repository.findOne(id);
	}

	@Transactional
	public Collection<User> findAll() {
		return repository.findAll();
	}

}
