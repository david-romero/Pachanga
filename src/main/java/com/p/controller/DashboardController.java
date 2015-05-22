package com.p.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.p.service.UsersService;
import com.p.util.Util;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController {

	// Definimos el controlador de logger.
	protected static Logger logger = Logger
			.getLogger(DashboardController.class);

	@Resource(name = "usersService")
	private UsersService usersService;

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public String getLogin(Model model) {
		return "dashboard";
	}
	
	@RequestMapping(value = "/indicadores", method = RequestMethod.GET,headers="Accept=*/*",produces={ "application/json"})
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Object  getMap() {
		Map<String,Object> indicadores = Maps.newHashMap();
		
		indicadores.put("ECO-CITIZEN-FRIENDLY", Util.randDouble(0, 100));
		
		indicadores.put("REDUCCIÓN-EN-EL-CONSUMO-ENERGÉTICO", Util.randDouble(0, 100));
		
		indicadores.put("INTEGRACION-DE-GENERACIÓN-RENOVABLE", Util.randDouble(0, 100));
		
		indicadores.put("CALIDAD-DEL-SUMINISTRO-ELÉCTRICO", Util.randDouble(0, 100));
		
		List<String> rating = Lists.newArrayList("A","B","C","D","E","F","G");
		
		Random r = new Random();
		
		indicadores.put("RATING",rating.get(r.nextInt(rating.size())));
		
		return indicadores;
	}

}
