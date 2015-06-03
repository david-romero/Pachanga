package com.p.controller.rest;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.p.model.Mensaje;
import com.p.model.Notificacion;
import com.p.model.User;

@RestController
@RequestMapping(value = "/rest/notificacion")
public class NotficacionController {

	@RequestMapping(value = "/obtenerTodas/{pagina}", method = RequestMethod.GET)
	public List<Notificacion> getConversacion(Model model,
			@PathVariable(value = "pagina") Integer pagina) {
		List<Notificacion> list = Lists.newArrayList();

		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		User userConversation = new User();
		userConversation.setId(1);

		User userConversationSigned = new User();
		userConversationSigned.setEmail(userSigned.getUsername());
		userConversationSigned.setId(999);

		Random rd = new Random();
		Integer numeroMensajes = rd.nextInt(10);
		for (int i = 0; i < numeroMensajes; i++) {

			Notificacion msj = new Notificacion();
			msj.setContenido("Nuevo mensaje  de " + 1L + " para  "
					+ userSigned.getUsername());
			msj.setTitulo("Titulo de la notificacion 1");
			Date fecha = new Date(System.currentTimeMillis());
			msj.setFecha(fecha);
			msj.setEmisor(userConversation);
			msj.setReceptor(userConversationSigned);
			list.add(msj);

			msj = new Notificacion();
			msj.setContenido("Nuevo mensaje  de " + userSigned.getUsername()
					+ " para  " + 1L);
			msj.setTitulo("Titulo de la notificacion 2");
			fecha = new Date(System.currentTimeMillis());
			msj.setFecha(fecha);
			msj.setEmisor(userConversationSigned);
			msj.setReceptor(userConversation);
			list.add(msj);

		}

		return list;
	}
	
	@RequestMapping(value = "/obtenerNumeroPaginas", method = RequestMethod.GET)
	public Integer getConversacion(Model model) {
		return 5;
	}

}
