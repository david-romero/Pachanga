package com.p.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.p.service.UsersService;

@Controller
@RequestMapping(value = "/registro")
public class RegistroController {

	// Definimos el controlador de logger.
	protected static Logger logger = Logger
			.getLogger(DashboardController.class);

	@Resource(name = "usersService")
	private UsersService usersService;

	
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody Object error() {
	    //handle error  
		return "Ha ocurrido un error guardando el registro";
	}

}
