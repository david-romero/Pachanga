package com.p.controller.rest;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.p.controller.AbstractController;
import com.p.model.Partido;
import com.p.model.User;
import com.p.service.UsersService;

@RestController
@RequestMapping(value = "/rest/usuarios")
@Transactional
public class UsuarioController extends AbstractController{
	
	@Autowired
	protected UsersService userService;
	
	
	
	@RequestMapping(value = "/inicio",method = RequestMethod.GET)
    public List<User> inicio() {		
        return Lists.newArrayList(userService.findAll());
    }
	
	@RequestMapping(value = "/editImage",method = RequestMethod.POST)
	public User save(Model model,@RequestParam("foto") MultipartFile file) {
		User usr = null;
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		try{
			beginTransaction();
			usr = userService.getByEmail(userSigned.getUsername());
			commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();
		}
		try{
			beginTransaction();
			usr.setImagen(file.getBytes());
			usr.setTieneAvatar(true);
			usr = userService.save(usr);
			txStatus.flush();
			commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();
		}
		return usr;
	}
	
}
