package com.p.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	
	@Autowired
	protected UsersService userService;
	
	@Autowired
	private MetricService metricService;

	private static final int PAGE_SIZE = 4;

	public Partido create() {
		Partido p = new Partido();
		p.setJugadores(Lists.newArrayList());
		p.setFecha(new Date(System.currentTimeMillis()));
		p.setPrecio(0.0);
		p.setPlazas(10);
		p.setPlazasOcupadas(0);
		p.setId(0);
		p.setTitulo("Partido nuevo");
		return p;
	}
	@Transactional
	public Partido findOne(Integer id) {
		Assert.notNull(id);
		Assert.isTrue(id > 0);
		return repository.findOne(id);
	}
	
	@Transactional
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
			//HIBERNATE Prevent LazyInitiliatiationException
		});
		return pagina;
	}
	
	@Transactional
	public Page<Partido> findAllPosiblesPublicos(Integer page) {
		PageRequest request = new PageRequest(page - 1, PAGE_SIZE,
				Sort.Direction.DESC, "fecha");
		Page<Partido> pagina = repository.getAllFuturosPublicosNoLlenos(request);
		return pagina;
	}
	
	@Transactional
	public Collection<Partido> findAllJugados(User usr) {
		Assert.notNull(usr);
		Assert.isTrue(usr.getId()>0);
		Collection<Partido> pagina = repository.getAllJugados(usr);
		return Lists.newArrayList(pagina).subList(0, pagina.size() >= 4 ? 4 : pagina.size());
	}
	
	@Transactional
	public Partido save(Partido p) {
		if ( p.getId() == null || p.getId().equals(0) ){
			metricService.savePartido();
			if ( p.getPropietario() instanceof User ){
				metricService.savePartidoUsuario(( (User) p.getPropietario()));
			}
		}
		return repository.save(p);
	}
	@Transactional
	public Partido apuntarse(Partido partido, User user) {
		Assert.isTrue(!partido.getJugadores().contains(user));
		partido.getJugadores().add(user);
		
		user.getPartidosJugados().add(partido);
		
		userService.save(user);
		partido.setPlazasOcupadas(partido.getPlazasOcupadas() + 1);
		partido = save(partido);
		
		return partido;
	}
	@Transactional(readOnly=true)
	public Page<Partido> findAllRelacionados(com.p.model.modelAux.Page page) {
		PageRequest request = new PageRequest(page.getPage() - 1, page.getPageSize(),
				Sort.Direction.DESC, "fecha");
		User usr = userService.getPrincipal();
		Assert.notNull(usr);
		return repository.getAllVinculados(usr,usr.getId(),request);
	}
	@Transactional
	public void remove(Integer idPartido) {
		Assert.notNull(idPartido);
		Assert.isTrue(idPartido > 0);
		Partido p = findOne(idPartido);
		
		p.getPropietario().getPartidosCreados().remove(p);
		
		p.setPropietario(null);
		
		repository.delete(p);
	}
	
	@Transactional
	public Partido eliminarJugador(User userSigned, User usuarioAEliminar, Partido pachanga) {
		Assert.notNull(pachanga);
		Assert.notNull(usuarioAEliminar);
		Assert.notNull(userSigned);
		Assert.isTrue(!usuarioAEliminar.equals(userSigned));
		Assert.isTrue(pachanga.getPropietario().equals(userSigned));
		Assert.isTrue(pachanga.getJugadores().contains(usuarioAEliminar));
		Assert.isTrue(usuarioAEliminar.getPartidosJugados().contains(pachanga));
		boolean eliminado = pachanga.getJugadores().remove(usuarioAEliminar);
		
		Assert.isTrue(eliminado);
		
		eliminado = usuarioAEliminar.getPartidosJugados().remove(pachanga);
		
		Assert.isTrue(eliminado);
		
		save(pachanga);
		
		userService.save(usuarioAEliminar);
		
		return pachanga;
	}

}
