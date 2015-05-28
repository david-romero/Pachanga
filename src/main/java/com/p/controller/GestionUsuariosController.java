package com.p.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.p.model.User;
import com.p.service.UsersService;

@Controller
@RequestMapping(value = "/usuarios")
public class GestionUsuariosController {

	RestTemplate restTemplate = new RestTemplate();
	// Definimos el controlador de logger.
	protected static Logger logger = Logger
			.getLogger(GestionUsuariosController.class);

	@Resource(name = "usersService")
	private UsersService usersService;

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
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String name = auth.getName();

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

	@RequestMapping(value = "/eliminarUsuario/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
	public String postDeleteUser(Model model, HttpSession session,
			@PathVariable(value = "id") String idUsuario) {
		String res;
		try {
			Long idCTBox = Long.parseLong(session.getAttribute("IDCtbox")
					.toString());

			// Prepare acceptable media type
			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

			// Prepare header
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(acceptableMediaTypes);
			String auth = "endesa" + ":" + "endesa";
			byte[] encodedAuth = org.springframework.security.crypto.codec.Base64
					.encode(auth.getBytes(Charset.forName("US-ASCII")));

			String authHeader = "Basic " + new String(encodedAuth);
			headers.set("Authorization", authHeader);

			res = "usuarios";
		} catch (Exception e) {
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}

		return res;
	}

	@RequestMapping(value = "/anadirUsuario/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
	public String postAddUser(Model model, HttpSession session,
			@PathVariable(value = "id") String idUsuario) {
		String res;
		try {
			Long idCTBox = Long.parseLong(session.getAttribute("IDCtbox")
					.toString());

			// Prepare acceptable media type
			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

			// Prepare header
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(acceptableMediaTypes);
			String auth = "endesa" + ":" + "endesa";
			byte[] encodedAuth = org.springframework.security.crypto.codec.Base64
					.encode(auth.getBytes(Charset.forName("US-ASCII")));

			String authHeader = "Basic " + new String(encodedAuth);
			headers.set("Authorization", authHeader);

			// Añadimos el nuevo usuario:
			List<String> usuarioAnadir = new ArrayList<String>();
			usuarioAnadir.add(idUsuario);

			res = "usuarios";
		} catch (Exception e) {
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}

		return res;
	}

	@RequestMapping(value = "/getUserImage/{username}")
	public void getUserImage(HttpServletResponse response,
			@PathVariable("username") String username) throws IOException {

		response.setContentType("image/jpeg");
		byte[] buffer = null;
		User user = usersService.getByEmail(username);
		if (user == null || user.getImagen() == null) {
			InputStream in = this.getClass().getResourceAsStream("/profile-pic-300px.jpg");

			buffer = IOUtils.toByteArray(in);
		} else {
			buffer = usersService.getByEmail(username).getImagen();
		}
		InputStream in1 = new ByteArrayInputStream(buffer);
		IOUtils.copy(in1, response.getOutputStream());
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getProfile(Model model) {
		String res = "";
		org.springframework.security.core.userdetails.User principal =  (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User usr = new User();
		usr.setEmail(principal.getUsername());
		usr.setPassword(principal.getPassword());
		model.addAttribute("userSigned", usr);
		try {
			//TODO - DRA - Profile
			res = "profile";
		} catch (Exception e) {
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}

		return res;
	}
	
	@RequestMapping(value = "/profile/{idUsuario}", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getProfile(Model model, @PathVariable("idUsuario") Integer idUsuario) {
		String res = "";
		User usr = new User();
		usr.setId(new Long(idUsuario));
		usr.setEmail("aasdasdas@asdasd.com");
		model.addAttribute("userSigned", usr);
		try {
			//TODO - DRA - Profile
			res = "profile";
		} catch (Exception e) {
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}

		return res;
	}
	
	@RequestMapping(value = "/chat", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getChat(Model model) {
		String res = "";

		try {
			//TODO - DRA - Chat
			res = "chat";
		} catch (Exception e) {
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}

		return res;
	}
	
	@RequestMapping(value = "/notificaciones", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getNotificaciones(Model model) {
		String res = "";

		try {
			//TODO - DRA - Notificaciones
			res = "notificaciones";
		} catch (Exception e) {
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}

		return res;
	}

}
