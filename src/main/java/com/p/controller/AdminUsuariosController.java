package com.p.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.p.service.UsersService;

@Controller
@RequestMapping(value = "/vista/user/")
public class AdminUsuariosController {

	RestTemplate restTemplate = new RestTemplate();
	// Definimos el controlador de logger.
	protected static Logger logger = Logger
			.getLogger(AdminUsuariosController.class);

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/adminUsuarios", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getAdminUsuariosAction(Model model) {
		String res = "";

		try {
			
			res = "adminUsuarios";
		} catch (Exception e) {
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}

		return res;
	}



	@RequestMapping(value = "/remove/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
	public String postRemove(Model model,
			@PathVariable(value = "id") String idUsuario) {

		String res;
		try {
			Long id = Long.valueOf(idUsuario);
			usersService.delete(id);

			model.addAttribute("error", "Usuario eliminado correctamente.");
			
			res = "adminUsuarios";
		} catch (Exception e) {
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}

		return res;
	}
}
