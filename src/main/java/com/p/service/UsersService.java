package com.p.service;

import java.util.Collection;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.p.model.User;
import com.p.model.repositories.UserRepository;

@Service("usersService")
@Transactional(isolation=Isolation.READ_UNCOMMITTED)
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
		if ( us.getAvatar() == null ){
			Random rd = new Random();
			us.setAvatar(User.avatarCss[rd.nextInt(User.avatarCss.length)]);
		}
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
		Assert.isTrue(id > -1);
		return repository.findOne(id);
	}

	@Transactional
	public Collection<User> findAll() {
		return repository.findAll();
	}

	@Transactional
	public Collection<User> findAllDifferent(String email) {
		
		return repository.findAllDifferent(email);
	}
	
	@Transactional(readOnly=true)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public  User getPrincipal() {
		User result;
		SecurityContext context;
		Authentication authentication;
		Object principal;

		// If the asserts in this method fail, then you're
		// likely to have your Tomcat's working directory
		// corrupt. Please, clear your browser's cache, stop
		// Tomcat, update your Maven's project configuration,
		// clean your project, clean Tomcat's working directory,
		// republish your project, and start it over.

		context = SecurityContextHolder.getContext();
		Assert.notNull(context);
		authentication = context.getAuthentication();
		Assert.notNull(authentication);
		principal = authentication.getPrincipal();
		Assert.isTrue(principal instanceof org.springframework.security.core.userdetails.User);
		result =  getByEmail( ((org.springframework.security.core.userdetails.User) principal).getUsername() );
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);

		return result;
	}

}
