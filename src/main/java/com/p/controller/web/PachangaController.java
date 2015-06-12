package com.p.controller.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.p.controller.AbstractController;
import com.p.model.Partido;
import com.p.model.PropietarioPartido;
import com.p.model.User;
import com.p.service.GrupoService;
import com.p.service.PartidoService;
import com.p.service.UsersService;

@Controller
@Transactional()
@RequestMapping(value = "/partido")
public class PachangaController extends AbstractController{

	@Autowired
	protected PartidoService partidoService;
	@Autowired
	protected UsersService userService;
	@Autowired
	protected GrupoService grupoService;
	
	private final static Logger log = Logger.getLogger(PachangaController.class);
	
	@RequestMapping(value = "/create",method = RequestMethod.GET, headers = "Accept=application/json")
	public String create(Model model,@RequestParam(value="propietario",required=false) Integer propietarioId) {
		PropietarioPartido propietario = null;
		User userSigned = findUser();
		if ( propietarioId == null ){
			propietario = userSigned;
		}else{
			try{
				beginTransaction(true);
				propietario = grupoService.findOne(propietarioId);
				commitTransaction();
			}catch(Exception e){
				log.error(e);
				rollbackTransaction();
			}
		}
		Partido p = partidoService.create();
		p.setPropietario(propietario);

		model.addAttribute("userSigned", userSigned);
		model.addAttribute("partido", p);
		return "partido";
	}
	
	@RequestMapping(value = "/show/{partidoId}",method = RequestMethod.GET, headers = "Accept=application/json")
	public String show(Model model,
			@PathVariable(value = "partidoId") Integer partidoId) {
		Partido p = null;
		try{
			beginTransaction(true);
			p = partidoService.findOne(partidoId);
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
		
		User usr = new User();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			usr = findUser();
		}else{
			usr.setEmail("anonymous@anonymous.com");
		}
		
		model.addAttribute("userSigned", usr);
		model.addAttribute("partido", p);
		return "partido";
	}
	
	private User findUser() {
		User usr = null;
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		try {
			beginTransaction(true);
			usr = userService.getByEmail(userSigned.getUsername());
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		return usr;
	}
	
}
