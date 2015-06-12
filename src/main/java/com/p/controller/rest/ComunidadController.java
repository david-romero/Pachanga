package com.p.controller.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.p.controller.AbstractController;
import com.p.model.Grupo;
import com.p.model.Mensaje;
import com.p.model.Partido;
import com.p.model.User;
import com.p.service.GrupoService;
import com.p.service.MensajeService;
import com.p.service.UsersService;

@RestController
@Transactional()
@RequestMapping(value = "/rest/comunidad")
public class ComunidadController extends AbstractController{
	
	@Autowired
	protected GrupoService service;
	
	@Autowired
	protected UsersService userService;
	
	@Autowired
	protected MensajeService mensajeService;
	
	private final static Logger log = Logger.getLogger(ComunidadController.class);

	@RequestMapping(value = "/partidos/{idComunidad}", method = RequestMethod.GET)
	public Collection<Partido> inicio(@PathVariable(value = "idComunidad") Integer idComunidad) {
		Grupo grp = null;
		List<Partido> partidosCreados = Lists.newArrayList();
		try{
			beginTransaction(true);
			grp = service.findOne(idComunidad);
			Hibernate.initialize(grp.getPartidosCreados());
			partidosCreados.addAll(grp.getPartidosCreados());
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			rollbackTransaction();
		}
		Assert.notNull(grp);
		return partidosCreados;
	}
	
	@RequestMapping(value = "/editImage/{idComunidad}",method = RequestMethod.POST)
	public Grupo save(Model model,@RequestParam("foto") MultipartFile file,
			@PathVariable(value = "idComunidad") Integer idComunidad) {
		Grupo grp = null;
		try{
			beginTransaction();
			grp = service.findOne(idComunidad);
			commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();
		}
		Assert.notNull(grp);
		try{
			beginTransaction();
			grp.setImagen(file.getBytes());
			grp = service.save(grp);
			txStatus.flush();
			commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();
		}
		return grp;
	}
	
	@RequestMapping(value = "/getImage/{id}")
	public void getGrupoImage(HttpServletResponse response,
			@PathVariable("id") Integer id) throws IOException {

		response.setContentType("image/jpeg");
		byte[] buffer = null;
		Grupo grp = null;
		try{
			beginTransaction();
			grp = service.findOne(id);
			commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();
		}
		if ( grp.getImagen() == null || grp.getImagen().length == 0) {
			InputStream in = this.getClass().getResourceAsStream("/profile-pic-300px.jpg");
			buffer = IOUtils.toByteArray(in);
		} else {
			buffer = grp.getImagen();
		}
		InputStream in1 = new ByteArrayInputStream(buffer);
		IOUtils.copy(in1, response.getOutputStream());
	}
	
	@RequestMapping(value = "/mensajes/{idComunidad}", method = RequestMethod.GET)
	public Collection<Mensaje> mensajes(@PathVariable(value = "idComunidad") Integer idComunidad) {
		List<Mensaje> mensajes = Lists.newArrayList();
		try{
			beginTransaction();
			mensajes = service.findMensajes(idComunidad);
			commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();
		}
		return mensajes;
	}
	
	@RequestMapping(value = "/mensaje/{idComunidad}", method = RequestMethod.POST)
	public Mensaje guardarMensaje(@PathVariable(value = "idComunidad") Integer idComunidad,
			@RequestBody Mensaje mensaje) {
		Mensaje mensajeCopia = new Mensaje();
		Assert.notNull(mensaje.getContenido());
		Assert.isTrue(mensaje.getContenido().length() > 0);
		Grupo grp = null;
		try{
			beginTransaction();
			grp = service.findOne(idComunidad);
			Hibernate.initialize(grp.getUsuarios());
			commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();
		}
		Assert.notNull(grp);
		User usr = findUserSigned();
		try{
			beginTransaction();
			mensajeCopia.setEmisor(usr);
			
			Optional<User> receptor = grp.getUsuarios().stream().filter(usuario->{
				return !usuario.getId().equals(usr.getId());
			}).findFirst();
			Assert.isTrue(receptor.isPresent());
			mensajeCopia.setReceptor(receptor.get());
			mensajeCopia.setContenido(mensaje.getContenido());
			mensajeCopia.setFecha(new Date(System.currentTimeMillis()));
			mensajeCopia.setPropietario(grp);
			mensajeCopia = mensajeService.save(mensajeCopia);
			commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();
		}
		return mensajeCopia;
	}
	

	
}
