package com.p.controller.rest;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.p.controller.AbstractController;
import com.p.model.Notificacion;
import com.p.model.User;
import com.p.service.NotificacionService;

@RestController
@Transactional()
@RequestMapping(value = "/rest/notificacion")
public class NotficacionController extends AbstractController {

	@Autowired
	protected NotificacionService notificacionService;

	protected static final Logger log = Logger
			.getLogger(NotficacionController.class);

	@RequestMapping(value = "/obtenerNoLeidas", method = RequestMethod.GET)
	public Collection<? extends Notificacion> getNotificacionesNoLeidas(Model model) {
		Collection<? extends Notificacion> notificaciones = null;
		User usrSigned = findUserSigned();
		
		try {
			beginTransaction(true);
			Optional<Integer> optional = Optional.empty();
			notificaciones = notificacionService.findUltimasNoLeidas(usrSigned,optional);
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}

		return notificaciones;
	}

	@RequestMapping(value = "/eliminar", method = RequestMethod.POST)
	public void eliminar(Model model, @RequestBody Map<String,List<Notificacion>> notificaciones) {
		for ( Notificacion notificacion : notificaciones.get("notificaciones") ){
			try{
				beginTransaction(false);
				notificacionService.remove(notificacion);
				commitTransaction();
			}catch(Exception e){
				log.error(e);
				rollbackTransaction();
			}
		}
	}
	
	@RequestMapping(value = "/marcarLeidas", method = RequestMethod.POST)
	public void marcarLeidas(Model model, @RequestBody Map<String,List<Notificacion>> notificaciones) {
		for ( Notificacion notificacion : notificaciones.get("notificaciones") ){
			try{
				beginTransaction(false);
				notificacion.setLeido(true);
				notificacionService.save(notificacion);
				commitTransaction();
			}catch(Exception e){
				log.error(e);
				rollbackTransaction();
			}
		}
	}

	@RequestMapping(value = "/obtenerUltimas/", method = RequestMethod.GET)
	public List<Notificacion> getUltimasNtoficaciones() {
		List<Notificacion> list = Lists.newArrayList();
		User usrSigned = findUserSigned();
		try {
			beginTransaction(true);
			Optional<Integer> optional = Optional.of(5);
			list.addAll(notificacionService.findUltimasNoLeidas(usrSigned,optional));
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		if ( list.size() > 5 ){
			list = list.subList(0, 4);
		}
		return list;
	}

	@RequestMapping(value = "/leerTodasNoLeidas", method = RequestMethod.GET)
	public void leerTodasNoLeidas() {
		List<Notificacion> list = getUltimasNtoficaciones();
		try {
			beginTransaction();
			for (Notificacion noti : list) {
				noti.setLeido(true);
				notificacionService.save(noti);
			}
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
	}

	
}
