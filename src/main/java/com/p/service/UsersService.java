package com.p.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.p.model.User;
import com.p.model.repositories.UserRepository;

@SuppressWarnings("unchecked")
@Service("usersService")
@Transactional
public class UsersService {

	protected static Logger logger = Logger.getLogger("service");

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	@Autowired
	private UserRepository repository;

	public UsersService() {

	}

	/**
	 * Devuelve todos los usuarios
	 * 
	 * @return listado de todos los usuarios
	 */
	public List<User> getAll() {

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		Query query = session
				.createQuery("FROM User e where id !='USUARIO_ERRONEO' order by e.id desc");

		// Retrieve all
		return query.list();
	}

	public List<User> getAllCTBox() {

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		Query query = session
				.createQuery("FROM User e where id !='USUARIO_ERRONEO' and usu_nivel_id in(2,4) order by e.id desc");

		// Retrieve all
		return query.list();
	}

	public List<User> getAllApp() {

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		Query query = session
				.createQuery("FROM User e where id !='USUARIO_ERRONEO' and (usu_nivel_id not in(1,5) or usu_nivel_id is null) order by e.id desc");

		// Retrieve all
		return query.list();
	}

	/**
	 * Devuelve todos los usuarios que no están en la lista especificada.
	 * 
	 * @return listado de todos los usuarios que no están en la lista
	 *         especificada.
	 */
	public List<User> getNotAllCTBox(List<String> listaUsuarios) {

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		Query query = session
				.createQuery(
						"FROM User e where id not in(:listaUsuarios) and id !='USUARIO_ERRONEO' and usu_nivel_id in(2,4) order by e.id desc")
				.setParameterList("listaUsuarios", listaUsuarios);

		// Retrieve all
		return query.list();
	}

	/**
	 * Devuelve todos los usuarios para un dispositivo en concreto
	 * 
	 * @param id
	 *            del dispositivo
	 * 
	 * @return listado de usuarios
	 */
	public User getAllByDevice(Integer user_id) {

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM  User e where e.id=:user")
				.setParameter("user", user_id);

		// Retrieve all
		if (query.list().size() > 0) {
			return (User) query.list().get(0);
		} else {
			return null;
		}
	}

	/**
	 * Devuelve un usuario
	 * 
	 * @param id
	 *            del usuario
	 * 
	 */
	public User get(String id) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing person first
		User usuario = (User) session.get(User.class, id);
		
		return usuario;
	}

	public User getByLogin(String login) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM  User e where upper(e.user)=upper(:user)")
				.setParameter("user", login);

		// Retrieve all
		if (query.list().size() > 0) {
			return (User) query.list().get(0);
		} else {
			return null;
		}
	}

	/**
	 * añade un nuevo usuario
	 */
	public void add(User usuario) {

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Save
		session.save(usuario);
	}
	

	/**
	 * actualiza un usuario
	 */
	public void update(User usuario) {
		Assert.notNull(usuario);
		Assert.notNull(usuario.getId());
		Assert.isTrue(usuario.getId() > 0);
		repository.save(usuario);
	}

	/**
	 * Borra un usuario según sea usuari de la web (su id empieza por 1) o usuario de llavero(su id empieza por 0)
	 * 
	 * @param id
	 *            el id del usuario existente
	 */
	public void delete(Long id) {
		Assert.notNull(id);
		Assert.isTrue(id > 0);
		repository.delete(id);
	}


	/**
	 * Guarda o edita sengún si el ID esta o no relleno
	 * 
	 * @param us
	 */
	@Transactional
	public void save(User us) {
		if (us.getId() == null || us.getId().equals(0L)) {
			add(us);
		} else {
			update(us);
		}

	}
	
	public void deleteConRFID(User usuario) {

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		String strQuery = "update User set nombre = null, "
				+ "apellido1 = null," + "apellido2 = null,"
				+ "dni = null," + "empresa = null," + "nivel = null,"
				+ "pass = null, user = null where id=:id";

		Query query = session.createQuery(strQuery)
				.setParameter("id", usuario.getId());

		
		// Save
		query.executeUpdate();
	}

	public User getByEmail(String login) {
		Assert.notNull(login);
		Assert.isTrue(login.length() > 0);
		return repository.findByEmail(login);
	}
	
	public User findOne(Long id) {
		Assert.notNull(id);
		Assert.isTrue(id > 0L);
		return repository.findOne(id);
	}
}
