package com.p.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.p.controller.AbstractController;
import com.p.model.User;

@Controller()
@Transactional()
@RequestMapping(value = "/mensajes")
public class MensajeController extends AbstractController {

	@RequestMapping( method = RequestMethod.GET, headers = "Accept=application/json")
	public String getChat(Model model) {
		String res = "";

		try {
			//TODO - DRA - Chat
			User usr = findUserSigned();
			model.addAttribute("userSigned",usr);
			res = "chat";
		} catch (Exception e) {
			model.addAttribute("errorweb", e);
			res = "errorweb";
		}

		return res;
	}
	
	@RequestMapping(value="/{idUsuarioReceptor}", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getChatParaUsuario(Model model,@PathVariable("idUsuarioReceptor") Integer idUsuarioReceptor) {
		String res = "";
		User usr = findUserSigned();
		model.addAttribute("userSigned",usr);
		res = "chat";
		try {
			beginTransaction(true);
			User usrReceptor = userService.findOne(idUsuarioReceptor);
			model.addAttribute("usrReceptor",usrReceptor);
			commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
}
