package com.p.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.p.model.ChangePass;
import com.p.model.User;
import com.p.service.UsersService;

@Controller
@RequestMapping
public class LoginController {
	
	// Definimos el controlador de logger.
	protected static Logger logger = Logger
			.getLogger(AdminUsuariosController.class);

	
	@Resource(name = "usersService")
	private UsersService usersService;
	
	@RequestMapping(value = "/vista", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getLoginAction(Model model) {
		String res;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		
		final ExecutorService service = Executors.newFixedThreadPool(50);
		
		
		List<Long> listadosCer_ori = new ArrayList<Long>();
		
		
		Map<Object, Object> params = new TreeMap<Object, Object>();
		ArrayList<Object> listMapCTboxEnvio = new ArrayList<Object>();


		model.addAttribute("listCTbox", params);
		

		
		service.shutdown();
		
		model.addAttribute("listMapCTbox", listMapCTboxEnvio);
		res = "index";
		return res;
	}
	
	
	@RequestMapping(value = "/map", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String getMap(@RequestParam(value="consultaCT", required = false, defaultValue="false") boolean consultaCT) {
		String res = "";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		
		
		final ExecutorService service = Executors.newFixedThreadPool(50);
				
		service.shutdown();
		return res;
	}
	
	
	

	@RequestMapping(value = "/secure", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getLogin(Model model,
			@RequestParam(value = "error", required = false) boolean error) {
		
		String res;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		        res = "redirect:/app";
		}else{
			if (error == true) {
				// Assign an error message
				model.addAttribute("error",	"Los datos introducidos no son correctos.");
			} else {
				model.addAttribute("error", "");
			}
			res = "login";
			
		}
		
		return res;
	}
	
	

	@RequestMapping(value = "/sign-out", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getLogout(Model model) {
		SecurityContextHolder.clearContext();
		String res = "redirect:/app";
			
		return res;
	}
	
	@RequestMapping(value = "/changePass", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getChangePass(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();

		List<Long> listadosCer = new ArrayList<Long>();
		
		Map<Object, Object> params = new TreeMap<Object, Object>();


		if (listadosCer.size() != 0) {
			for (Long usuarioCTBox : listadosCer) {
				params.put(usuarioCTBox, usuarioCTBox);
			}
		} 
		model.addAttribute("listCTbox", params);	
		
		model.addAttribute("changePass", new ChangePass());
		return "changePass";
	}
	
	@RequestMapping(value = "/actualizarPass", method = RequestMethod.POST, headers = "Accept=application/json")
	public String getChangePass(Model model,
			@ModelAttribute("changePass") ChangePass cambiarPass)
			throws NoSuchAlgorithmException {

		try {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String name = auth.getName();
			User user = usersService.getByLogin(name);
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			if (cambiarPass.getPassNuevo().length() < 8) {
				model.addAttribute("errorWeb",
						"La contraseña debe tener una longitud mínima de 8 caracteres.");
			} else if (!cambiarPass.getPassNuevo().equals(
					cambiarPass.getPassValidar())) {
				model.addAttribute("errorWeb",
						"Las contraseñas indicadas no coinciden.");
			} else if (!user.getPassword().equals( encoder.encodePassword(cambiarPass.getPassActual(),null) )) {
				model.addAttribute("errorWeb",
						"La contraseña actual no corresponte con la indicada.");
			} else {
				
				String hashedPass = encoder.encodePassword(cambiarPass.getPassNuevo(), null);
				user.setPassword(hashedPass);
				usersService.update(user);
				model.addAttribute("ok",
						"Se ha actualizado la contraseña correctamente.");
			}

	
		} catch (Exception e) {
			logger.error(e);
		}
		return "changePass";
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getAccesDenied(Model model) {
		
		String res;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth instanceof AnonymousAuthenticationToken) {
	        res = "redirect:/login";
		}else{	
			res = "denied";
		}
		
		return res;
	}
}
