package com.p.controller.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;
import com.p.controller.AbstractController;
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
	

	
	
	
}
