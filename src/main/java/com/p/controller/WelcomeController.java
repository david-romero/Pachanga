package com.p.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/app")
public class WelcomeController {

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public String index(Model model){
		return "index";
		
	}
	
}
