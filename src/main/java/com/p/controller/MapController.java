package com.p.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Maps;
import com.p.model.modelAux.Pair;

@Controller
@RequestMapping(value = "/mapa")
public class MapController {

	static Map<String,List<Pair<Double,Double>>> poligonos = Maps.newHashMap();
	
	@RequestMapping(value = "/inicio", method = RequestMethod.POST,headers="Accept=*/*",produces={ "application/json"})
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Object  getMap() {
		
		
		
		return "OK";
	}
	
}
