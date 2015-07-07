/**
 * 
 */
package com.p.service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import com.p.model.Novedad.NovedadType;
import com.p.model.Partido;
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
		novedad.setTipo(NovedadType.NOTICIA);
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

	/**
	 * Crea una novedad para cuando un usuario crea un partido
	 * @param partido
	 * @return
	 */
	public Novedad create(Partido partido) {
		User user = (User) partido.getPropietario();
		Novedad novedad = create();
		novedad.setUsuario(user);
		StringBuilder builder = new StringBuilder(user.getScreenName());
		builder.append("ha creado un partido ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		builder.append(partido.getTitulo() + " ");
		builder.append("para la fecha ");
		builder.append(formatter.format(partido.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
		novedad.setTipo(NovedadType.PARTIDO);
		novedad.setContenido(builder.toString());
		novedad.setPartido(partido);
		return save(novedad);
	}

	/**
	 * Crea una novedad para cuando un usuario se apunta a un partido
	 * @param partido
	 * @param user
	 * @return
	 */
	public Novedad create(Partido partido, User user) {
		Novedad novedad = create();
		novedad.setUsuario(user);
		StringBuilder builder = new StringBuilder(user.getScreenName());
		builder.append("se ha apuntado al partido ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		builder.append(partido.getTitulo() + " ");
		builder.append("para la fecha ");
		builder.append(formatter.format(partido.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
		if ( partido.getPlazasOcupadas() < partido.getPlazas() ){
			builder.append("Faltan " + (partido.getPlazas() - partido.getPlazasOcupadas()) + " jugadores. ¿Te animas? " );
		}
		novedad.setTipo(NovedadType.PARTIDO);
		novedad.setContenido(builder.toString());
		novedad.setPartido(partido);
		if ( partido.getPropietario() instanceof User ){
			novedad.setEmisor((User) partido.getPropietario());
		}
		return save(novedad);
	}
	
	public Novedad create() {
		Novedad novedad = new Novedad();
		novedad.setLikes(0);
		novedad.setFecha(new Date(System.currentTimeMillis()));
		return novedad;
	}

}
