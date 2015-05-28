package com.p.controller.rest;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.p.model.Mensaje;
import com.p.model.User;

@RestController
@RequestMapping(value = "/rest/mensaje")
public class MensajeController {

	@RequestMapping(value = "/conversacion/{idUsuario}", method = RequestMethod.GET)
	public List<Mensaje> getConversacion(Model model,
			@PathVariable(value = "idUsuario") Integer idUsuario) {

		List<Mensaje> list = Lists.newArrayList();
		
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		User userConversation = new User();
		userConversation.setId(new Long(idUsuario));
		
		User userConversationSigned = new User();
		userConversationSigned.setEmail(userSigned.getUsername());
		userConversationSigned.setId(999L);
		
		
		Random rd = new Random();
		Integer numeroMensajes = rd.nextInt(15);
		for ( int i = 0; i < numeroMensajes; i++  ){
		
			Mensaje msj = new Mensaje();
			msj.setContenido("Nuevo mensaje  de " + idUsuario + " para  " + userSigned.getUsername());
			Date fecha = new Date(System.currentTimeMillis());
			msj.setFecha(fecha);
			msj.setEmisor(userConversation);
			msj.setReceptor(userConversationSigned);
			list.add(msj );
			
			msj = new Mensaje();
			msj.setContenido("Nuevo mensaje  de " + userSigned.getUsername() + " para  " + idUsuario);
			fecha = new Date(System.currentTimeMillis());
			msj.setFecha(fecha);
			msj.setEmisor(userConversationSigned);
			msj.setReceptor(userConversation);
			list.add(msj );
		
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
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		User userConversation = new User();
		userConversation.setId(new Long(idUsuarioReceptor));
		
		User userConversationSigned = new User();
		userConversationSigned.setId(new Long(idUsuarioEmisor));
		userConversationSigned.setEmail(userSigned.getUsername());
		
		mensaje.setContenido(mensaje.getContenido());
		Date fecha = new Date(System.currentTimeMillis());
		mensaje.setFecha(fecha);
		mensaje.setEmisor(userConversationSigned);
		mensaje.setReceptor(userConversation);
		return mensaje;
	}
	
	@RequestMapping(value = "/conversacion/{idUsuarioReceptor}", method = RequestMethod.POST)
	public Mensaje addMensaje(Model model,
			@PathVariable(value = "idUsuarioReceptor") Integer idUsuarioReceptor,
			@RequestBody Mensaje mensaje) {
		Assert.notNull(mensaje.getContenido());
		Assert.isTrue(mensaje.getContenido().length() > 0);
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		User userConversation = new User();
		userConversation.setId(new Long(idUsuarioReceptor));
		
		User userConversationSigned = new User();
		userConversationSigned.setEmail(userSigned.getUsername());
		
		mensaje.setContenido(mensaje.getContenido());
		Date fecha = new Date(System.currentTimeMillis());
		mensaje.setFecha(fecha);
		mensaje.setEmisor(userConversationSigned);
		mensaje.setReceptor(userConversation);
		return mensaje;
	}
	
}
