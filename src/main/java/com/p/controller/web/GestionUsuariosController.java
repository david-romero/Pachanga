package com.p.controller.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.p.controller.AbstractController;
import com.p.model.User;
import com.p.service.MetricStadisticsService;

@Controller
@RequestMapping(value = "/usuarios")
public class GestionUsuariosController extends AbstractController{

	// Definimos el controlador de logger.
	protected static Logger logger = Logger
			.getLogger(GestionUsuariosController.class);
	
	@Autowired
	protected MetricStadisticsService metricStadisticsService;

	@RequestMapping(value = "/getUserImage/{id}")
	public void getUserImage(HttpServletResponse response,
			@PathVariable("id") Integer id) throws IOException {

		response.setContentType("image/jpeg");
		byte[] buffer = null;
		try {
			beginTransaction(true);
			buffer = userService.findImage(id);
			commitTransaction();
		} catch (Exception e) {
			logger.error(e);
			rollbackTransaction();
		}
		if (buffer == null  || buffer.length == 0) {
			InputStream in = this.getClass().getResourceAsStream("/profile-pic-300px.jpg");

			buffer = IOUtils.toByteArray(in);
		} 
		InputStream in1 = new ByteArrayInputStream(buffer);
		IOUtils.copy(in1, response.getOutputStream());
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getProfile(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return "redirect:/login";
		}
		String res = "profile";
		User usr = findUserSigned();
		model.addAttribute("user", usr);
		model.addAttribute("userSigned", usr);
		Integer visitas = 0;
		try {
			beginTransaction(true);
			visitas = metricStadisticsService.getProfileVisitsStadistics(usr);
			commitTransaction();
		} catch (Exception e) {
			logger.error(e);
			rollbackTransaction();
		}
		model.addAttribute("visitas", visitas);
		return res;
	}
	
	@RequestMapping(value = "/profile/{idUsuario}", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getProfile(Model model, @PathVariable("idUsuario") Integer idUsuario) {
		String res = "";
		User usr = null;
		try {
			beginTransaction(true);
			usr = userService.findOne(idUsuario);
			commitTransaction();
			res = "profile";
		} catch (Exception e) {
			rollbackTransaction();
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}
		User userSigned = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			userSigned = findUserSigned();
		}else{
			userSigned = new User();
			userSigned.setId(-1);
		}
		model.addAttribute("userSigned", userSigned);
		model.addAttribute("user", usr);
		Integer visitas = 0;
		try {
			beginTransaction(true);
			visitas = metricStadisticsService.getProfileVisitsStadistics(usr);
			commitTransaction();
		} catch (Exception e) {
			logger.error(e);
			rollbackTransaction();
		}
		model.addAttribute("visitas", visitas);
		return res;
	}
	
	
	
	

}
