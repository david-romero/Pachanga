package com.p.controller.rest;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.p.controller.AbstractController;
import com.p.model.Mensaje;
import com.p.model.User;
import com.p.service.MensajeService;

@RestController(value="mensajeRestController")
@Transactional()
@RequestMapping(value = "/rest/mensaje")
public class MensajeController extends AbstractController{
	
	private static final Logger log = Logger.getLogger(MensajeController.class);
	
	@Autowired
	protected MensajeService mensajeService;

	@RequestMapping(value = "/conversacion/{idUsuario}", method = RequestMethod.GET)
	public List<Mensaje> getConversacion(Model model,
			@PathVariable(value = "idUsuario") Integer idUsuario) {

		List<Mensaje> list = Lists.newArrayList();
		
		User userSigned = findUserSigned();
		User userReceptor = null;
		userReceptor = getUserById(idUsuario);
		
		try{
			beginTransaction(true);
			list.addAll(mensajeService.findConversacion(userSigned,userReceptor));
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
		

		return list;
	}
	
	@RequestMapping(value = "/getMensajesSinLeer", method = RequestMethod.GET)
	public List<Mensaje> getMensajesSinLeer(Model model) {

		List<Mensaje> list = Lists.newArrayList();
		
		User userSigned = findUserSigned();
		
		try{
			beginTransaction(true);
			list.addAll(mensajeService.findMensajesSinLeer(userSigned));
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
		

		return list;
	}
	
	
	
	@RequestMapping(value = "/conversacion/{idUsuarioEmisor}/{idUsuarioReceptor}", method = RequestMethod.POST)
	public Mensaje addMensaje(Model model,
			@PathVariable(value = "idUsuarioEmisor") Integer idUsuarioEmisor,
			@PathVariable(value = "idUsuarioReceptor") Integer idUsuarioReceptor,
			@RequestBody Mensaje mensaje) {
		Assert.notNull(mensaje.getContenido());
		Assert.isTrue(mensaje.getContenido().length() > 0);
		
		User userSigned = findUserSigned();
		
		User userReceptor = null;
		User userEmisor = null;
		//Si el receptor no es el usuario loguqeado
		if ( !idUsuarioReceptor.equals(userSigned.getId())  ){
			userReceptor = getUserById(idUsuarioReceptor);
			//el id del usuario emisor recibido tiene que ser igual al del usuario signed
			if ( !userSigned.getId().equals(idUsuarioEmisor) ){
				throw new IllegalArgumentException("El usuario emisor debe ser el usuario registrado en el sistema");
			}else{
				userEmisor = userSigned;
			}
		}else{
			//El receptor es el usuario logueado
			userEmisor = getUserById(idUsuarioEmisor);
			userReceptor = userSigned;
		}
		
		
		Date fecha = new Date(System.currentTimeMillis());
		mensaje.setFecha(fecha);
		mensaje.setEmisor(userEmisor);
		mensaje.setReceptor(userReceptor);
		
		mensaje = save(mensaje);
		
		return mensaje;
	}

	protected Mensaje save(Mensaje mensaje) {
		try{
			beginTransaction();
			mensaje = mensajeService.save(mensaje);
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
		return mensaje;
	}
	
	@RequestMapping(value = "/conversacion/{idUsuarioReceptor}", method = RequestMethod.POST)
	public Mensaje addMensaje(Model model,
			@PathVariable(value = "idUsuarioReceptor") Integer idUsuarioReceptor,
			@RequestBody Mensaje mensaje) {
		Assert.notNull(mensaje.getContenido());
		Assert.isTrue(mensaje.getContenido().length() > 0);
		
		User userReceptor = null;
		User userSigned = findUserSigned();
		
		userReceptor = getUserById(idUsuarioReceptor);
		
		Assert.isTrue(!userReceptor.equals(userSigned));

		mensaje.setContenido(mensaje.getContenido());
		Date fecha = new Date(System.currentTimeMillis());
		mensaje.setFecha(fecha);
		mensaje.setEmisor(userSigned);
		mensaje.setReceptor(userReceptor);
		
		mensaje = save(mensaje);
		
		return mensaje;
	}
	
	@RequestMapping(value = "/conversacion/{idUsuarioReceptor}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> eliminarConversacion(Model model,
			@PathVariable(value = "idUsuarioReceptor") Integer idUsuarioReceptor) {
		ResponseEntity<Void> response = new ResponseEntity<Void>(HttpStatus.CONTINUE);
		User userReceptor = null;
		User userSigned = findUserSigned();
		
		userReceptor = getUserById(idUsuarioReceptor);
		
		Assert.isTrue(!userReceptor.equals(userSigned));
		
		try{
			beginTransaction();
			mensajeService.remove(mensajeService.findConversacion(userSigned, userReceptor));
			commitTransaction();
			response = new ResponseEntity<Void>(HttpStatus.OK);
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	protected User getUserById(Integer idUsuarioReceptor) {
		User userReceptor = null;
		try{
			beginTransaction(true);
			userReceptor = userService.findOne(idUsuarioReceptor);
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
		return userReceptor;
	}
	
	@RequestMapping(value = "/leerMensajes/{idUsuarioEmisor}/{idUsuarioReceptor}/", method = RequestMethod.GET)
	public Collection<Mensaje> establecerMensajesALeidos(Model model,
			@PathVariable(value = "idUsuarioEmisor") Integer idUsuarioEmisor,
			@PathVariable(value = "idUsuarioReceptor") Integer idUsuarioReceptor) {
		List<Mensaje> mensajes = Lists.newArrayList();
		try{
			beginTransaction(true);
			mensajes.addAll(mensajeService.findMensajesSinLeer(idUsuarioEmisor, idUsuarioReceptor));
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
		
		
		
		try{
			beginTransaction();
			for ( Mensaje mensaje : mensajes ){
				mensaje.setLeido(true);
				mensajeService.save(mensaje);
			}
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
		
		return mensajes;
		
		
	}
	
	
}
