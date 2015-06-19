package com.p.controller.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.common.collect.Lists;
import com.p.controller.AbstractController;
import com.p.controller.converters.ObjectCodec;
import com.p.model.Mensaje;
import com.p.model.Notificacion;
import com.p.service.MensajeService;
import com.p.service.NotificacionService;

@Controller()
@Transactional()
@RequestMapping(value = "/alertas")
public class AlertasController extends AbstractController{
	
	protected static final Logger log = Logger.getLogger(AlertasController.class);
	
	@Autowired
	protected NotificacionService notificacionService;
	
	@Autowired
	protected MensajeService mensajeService;

	@RequestMapping(value="/",method = RequestMethod.GET)
	public void sendMessage(HttpServletResponse response) throws IOException {
		response.setContentType("text/event-stream");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");
		response.setCharacterEncoding("UTF-8");

		
		List<Notificacion> notificaciones = null;
		try {
			beginTransaction(true);
			Optional<Integer> optional = Optional.of(5);
			notificaciones = Collections.synchronizedList(Lists.newArrayList(notificacionService.findUltimasNoLeidas(findUserSigned(),optional)));
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		
		List<Mensaje> mensajes = null;
		try {
			beginTransaction(true);
			mensajes =  Collections.synchronizedList(Lists.newArrayList(mensajeService.findMensajesSinLeer()));
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		
		String datos =  "event: notificaciones\n" +
						"data: " + writeNotificacionesJsonStream(notificaciones) + "\n\n" +
						"event: mensajes\n" +
						"data: " + writeMensajesJsonStream(mensajes) + "\n\n";
						
		
		response.getWriter().write(datos);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	public String writeNotificacionesJsonStream( List<Notificacion> notificaciones) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		PrettyTime pt = new PrettyTime(new Locale("ES","es"));
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
            jGenerator.writeStringField("fecha", sdf.format(notificacion.getFecha()));
            jGenerator.writeStringField("fechaRepresentacion",  pt.format(notificacion.getFecha()) );
            jGenerator.writeNumberField("id", notificacion.getId());
            jGenerator.writeEndObject();
        }
        jGenerator.writeEndArray(); // ]
        jGenerator.flush();
    	jGenerator.close();
    	return stream.toString();
    }
	
	public String writeMensajesJsonStream( List<Mensaje> mensajes) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		PrettyTime pt = new PrettyTime(new Locale("ES","es"));
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		JsonFactory jfactory = new JsonFactory();
		JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
		jGenerator.setCodec(new ObjectCodec());
		jGenerator.writeStartArray(); // [
        for (Mensaje notificacion : mensajes) {
        	jGenerator.writeStartObject();
        	jGenerator.writeStringField("contenido", notificacion.getContenido());
        	jGenerator.writeObjectField("receptor", notificacion.getReceptor());
        	jGenerator.writeObjectField("emisor", notificacion.getEmisor());
        	Date fechaMensaje = notificacion.getFecha();
        	jGenerator.writeStringField("fechaRepresentacion",  pt.format(fechaMensaje) );
            jGenerator.writeStringField("fecha", sdf.format(fechaMensaje));
            jGenerator.writeNumberField("id", notificacion.getId());
            jGenerator.writeEndObject();
        }
        jGenerator.writeEndArray(); // ]
        jGenerator.flush();
    	jGenerator.close();
    	return stream.toString();
    }
	
}
