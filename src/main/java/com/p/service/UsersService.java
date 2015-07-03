package com.p.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.p.model.Notificacion;
import com.p.model.Role;
import com.p.model.User;
import com.p.model.modelAux.RegisterUser;
import com.p.model.repositories.UserRepository;

@Service("usersService")
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public class UsersService {

	protected static Logger logger = Logger.getLogger("service");

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	@Autowired
	private UserRepository repository;

	@Autowired
	private NotificacionService notificacionService;

	@Autowired
	private EmailManager emailManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
		gestionarAvatar(us);
		gestionarAltaUsuario(us);
		User usr = repository.save(us);
		return usr;
	}

	protected void gestionarAltaUsuario(User us) {
		if (us.getId() == null || us.getId().equals(0)) {
			gestionarNotificacionAltaUsuario(us);
			gestionarEmailAltaUsuario(us);
		}
	}

	protected void gestionarEmailAltaUsuario(User us) {
		emailManager.notify(us);
	}

	/**
	 * @param us
	 */
	protected void gestionarNotificacionAltaUsuario(User us) {
		// Es nuevo usuario
		// Le enviamos un email y una notificacion
		Notificacion notificacion = notificacionService.create();
		Optional<User> admin = repository.findAdministradores().stream()
				.findFirst();
		Assert.isTrue(admin.isPresent());
		User administrador = admin.get();
		notificacion.setEmisor(administrador);
		notificacion.setReceptor(us);
		notificacion.setTitulo("Gracias por registrarte en Pachanga!");
		notificacion
				.setContenido("¿Porque no completas tu perfil? Quedará mucho más mono :)");
		notificacionService.save(notificacion);
	}

	/**
	 * @param us
	 */
	protected void gestionarAvatar(User us) {
		if (us.getAvatar() == null) {
			Random rd = new Random();
			us.setAvatar(User.avatarCss[rd.nextInt(User.avatarCss.length)]);
		}
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

	@Transactional(readOnly = true)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public User getPrincipal() {
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
		result = getByEmail(((org.springframework.security.core.userdetails.User) principal)
				.getUsername());
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);

		return result;
	}

	public User map(RegisterUser user) {
		User usr = create();
		usr.setEmail(user.getEmail());
		usr.setPassword(user.getPassword());
		return usr;
	}

	public User create() {
		User user = new User();
		user.setFirstName(" ");
		user.setLastName("  ");
		user.setRole(Role.ROLE_USER);
		return user;
	}
	@Transactional
	public void regenerarPassword(User user) {
		String newPass = UUID.randomUUID().toString();
		newPass = passwordEncoder.encode(newPass);
		user.setPassword(newPass);
		save(user);
		emailManager.notifyNewPassword(user,newPass);
	}
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public byte[] findImage(Integer id) {
		Assert.notNull(id);
		Assert.isTrue(id > 0);
		return repository.findImage(id);
	}
	
	@Transactional(readOnly = true)
	public Collection<? extends User> find(String texto) {
		return repository.findFullText(texto);
	}

}
