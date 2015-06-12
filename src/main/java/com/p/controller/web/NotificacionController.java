package com.p.controller.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.common.collect.Lists;
import com.p.controller.AbstractController;
import com.p.controller.converters.ObjectCodec;
import com.p.model.Notificacion;
import com.p.model.User;
import com.p.service.NotificacionService;

@Controller()
@Transactional()
@RequestMapping(value = "/notificaciones")
public class NotificacionController extends AbstractController {
	
	@Autowired
	protected NotificacionService notificacionService;
	
	protected static final Logger log = Logger.getLogger(NotificacionController.class);
	
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public String getNotificaciones(Model model) {
		String res = "";

		try {
			User usr = findUserSigned();
			model.addAttribute("userSigned",usr);
			res = "notificaciones";
		} catch (Exception e) {
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}

		return res;
	}
	
	@RequestMapping(value="/leidas",method = RequestMethod.GET, headers = "Accept=application/json")
	public String getNotificacionesLeidas(Model model) {
		User usr = findUserSigned();
		List<Notificacion> notificacionesLeidas = Lists.newArrayList();
		try {
			beginTransaction();
			notificacionesLeidas.addAll(notificacionService.findLeidas(usr));
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		model.addAttribute("userSigned",usr);
		model.addAttribute("notificaciones",notificacionesLeidas);
		return "notificacionesLeidas";
	}
	
	@RequestMapping(value="/alertas", method = RequestMethod.GET)
	public void sendMessage(HttpServletResponse response) throws IOException {
		response.setContentType("text/event-stream");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");
		response.setCharacterEncoding("UTF-8");

		
		List<Notificacion> l = null;
		try {
			beginTransaction(true);
			Optional<Integer> optional = Optional.of(5);
			l = Collections.synchronizedList(Lists.newArrayList(notificacionService.findUltimasNoLeidas(findUserSigned(),optional)));
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		response.getWriter().write("data: " + writeJsonStream(l) + "\n\n");
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	public String writeJsonStream( List<Notificacion> notificaciones) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		JsonFactory jfactory = new JsonFactory();
		JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
		jGenerator.setCodec(new ObjectCodec());
		jGenerator.writeStartArray(); // [
        for (Notificacion notificacion : notificaciones) {
        	jGenerator.writeStartObject();
        	jGenerator.writeStringField("titulo", notificacion.getTitulo());
        	jGenerator.writeStringField("contenido", notificacion.getContenido());
        	jGenerator.writeObjectField("receptor", notificacion.getReceptor());
        	jGenerator.writeObjectField("emisor", notificacion.getEmisor());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
            jGenerator.writeStringField("fecha", sdf.format(notificacion.getFecha()));
            jGenerator.writeNumberField("id", notificacion.getId());
            jGenerator.writeEndObject();
        }
        jGenerator.writeEndArray(); // ]
        jGenerator.flush();
    	jGenerator.close();
    	return stream.toString();
    }
	
}
