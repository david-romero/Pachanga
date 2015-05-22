package com.p.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.p.model.User;

@RestController
@RequestMapping(value = "/rest/usuarios")
public class UsuarioController {

	static List<User> usuarios = Lists.newArrayList();
	
	static{
		User user = new User();
		user.setEmail("test@test.com");
		user.setId(1L);
		user.setTieneAvatar(false);
		usuarios.add(user);
		user = new User();
		user.setEmail("test1@test.com");
		user.setId(2L);
		user.setTieneAvatar(true);
		usuarios.add(user);
		user = new User();
		user.setEmail("test2@test.com");
		user.setId(3L);
		user.setTieneAvatar(false);
		usuarios.add(user);
		user = new User();
		user.setEmail("test3@test.com");
		user.setId(4L);
		user.setTieneAvatar(true);
		usuarios.add(user);
		user = new User();
		user.setEmail("test4@test.com");
		user.setId(5L);
		user.setTieneAvatar(false);
		usuarios.add(user);
		user = new User();
		user.setEmail("test5@test.com");
		user.setId(6L);
		user.setTieneAvatar(true);
		usuarios.add(user);
		user = new User();
		user.setEmail("test6@test.com");
		user.setId(7L);
		user.setTieneAvatar(false);
		usuarios.add(user);
		user = new User();
		user.setEmail("test7@test.com");
		user.setId(8L);
		user.setTieneAvatar(true);
		usuarios.add(user);
	}
	
	@RequestMapping(value = "/inicio",method = RequestMethod.GET)
    public List<User> inicio() {		
        return usuarios;
    }
	
}
