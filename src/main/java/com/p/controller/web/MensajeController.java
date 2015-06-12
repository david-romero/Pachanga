package com.p.controller.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.common.collect.Lists;
import com.p.controller.AbstractController;
import com.p.controller.converters.ObjectCodec;
import com.p.model.Mensaje;
import com.p.model.User;
import com.p.service.MensajeService;

@Controller(value="messageController")
@Transactional()
@RequestMapping(value = "/mensajes")
public class MensajeController extends AbstractController{
	
	protected static final Logger log = Logger.getLogger(MensajeController.class) ;
	
	@Autowired
	protected MensajeService mensajeService;

	@RequestMapping(value="/alertas/{idReceptor}", method = RequestMethod.GET)
	public void sendMessage(HttpServletResponse response,
			@PathVariable(value = "idReceptor") Integer idReceptor) throws IOException {
		response.setContentType("text/event-stream");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");
		response.setCharacterEncoding("UTF-8");
		
		User userReceptor = null;
		
		try {
			beginTransaction(true);
			userReceptor = userService.findOne(idReceptor);
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		
		List<Mensaje> l = null;
		try {
			beginTransaction(true);
			l = Collections.synchronizedList(Lists.newArrayList(mensajeService.findMensajesSinLeer(findUserSigned(),userReceptor)));
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		response.getWriter().write("data: " + writeJsonStream(l) + "\n\n");
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	public String writeJsonStream( List<Mensaje> mensajes) throws IOException {
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
