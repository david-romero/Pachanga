package com.p.controller.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.p.controller.AbstractController;
import com.p.model.User;
import com.p.service.UsersService;

@RestController
@RequestMapping(value = "/rest/usuarios")
@Transactional
public class UsuarioController extends AbstractController{
	
	@Autowired
	protected UsersService userService;
	
	protected static final Logger log = Logger.getLogger(UsuarioController.class);
	
	@RequestMapping(value = "/inicio",method = RequestMethod.GET)
    public List<User> inicio() {	
		List<User> list = Lists.newArrayList();
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
        try{
			beginTransaction(true);
			list = Lists.newArrayList(userService.findAllDifferent(userSigned.getUsername()));
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
        return list;
    }
	
	@RequestMapping(value = "/editImage",method = RequestMethod.POST)
	public User save(Model model,@RequestParam("foto") MultipartFile file) {
		User usr = findUserSigned();
		try{
			beginTransaction();
			usr.setImagen(file.getBytes());
			usr.setTieneAvatar(true);
			usr = userService.save(usr);
			txStatus.flush();
			commitTransaction();
		}catch(Exception e){
			log.error(e);
			rollbackTransaction();
		}
		return usr;
	}
	
}
