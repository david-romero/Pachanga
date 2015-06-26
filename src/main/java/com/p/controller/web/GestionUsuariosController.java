package com.p.controller.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.p.controller.AbstractController;
import com.p.model.User;

@Controller
@RequestMapping(value = "/usuarios")
public class GestionUsuariosController extends AbstractController{

	RestTemplate restTemplate = new RestTemplate();
	// Definimos el controlador de logger.
	protected static Logger logger = Logger
			.getLogger(GestionUsuariosController.class);


	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getLoginAction(Model model,
			@PathVariable(value = "id") String ctbox) {
		String res;

		try {
			/*
			 * if (Funciones.isNumeric(ctbox)) { cargarDatoPagina(model, ctbox);
			 * 
			 * }
			 */
			res = "usuarios";

			// Cargamos los cts para el menu de la izquierda
			/*Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String name = auth.getName();*/

			List<Long> listadosCer = new ArrayList<Long>();

			Map<Object, Object> params = new TreeMap<Object, Object>();

			if (listadosCer.size() != 0) {
				for (Long usuarioCTBox : listadosCer) {
					params.put(usuarioCTBox, usuarioCTBox);
				}
			}
			model.addAttribute("listCTbox", params);
		} catch (Exception e) {
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}

		return res;
	}

	@RequestMapping(value = "/getUserImage/{id}")
	public void getUserImage(HttpServletResponse response,
			@PathVariable("id") Integer id) throws IOException {

		response.setContentType("image/jpeg");
		byte[] buffer = null;
		User user = null;
		try {
			beginTransaction(true);
			user = userService.findOne(id);
			commitTransaction();
		} catch (Exception e) {
			logger.error(e);
			rollbackTransaction();
		}
		if (user == null || user.getImagen() == null || user.getImagen().length == 0) {
			InputStream in = this.getClass().getResourceAsStream("/profile-pic-300px.jpg");

			buffer = IOUtils.toByteArray(in);
		} else {
			buffer = user.getImagen();
		}
		InputStream in1 = new ByteArrayInputStream(buffer);
		IOUtils.copy(in1, response.getOutputStream());
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getProfile(Model model) {
		String res = "";
		org.springframework.security.core.userdetails.User principal =  (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User usr = null;
		try {
			beginTransaction(true);
			usr = userService.getByEmail(principal.getUsername());
			commitTransaction();
			res = "profile";
		} catch (Exception e) {
			rollbackTransaction();
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}
		model.addAttribute("userSigned", usr);
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
		model.addAttribute("userSigned", usr);
		return res;
	}
	
	
	
	

}
