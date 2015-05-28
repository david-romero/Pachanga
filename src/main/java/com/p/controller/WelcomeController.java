package com.p.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/")
public class WelcomeController {

	@RequestMapping(value="/app",method = RequestMethod.GET, headers = "Accept=application/json")
	public String indexApp(Model model){
		return "index";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public String index(Model model){
		return "redirect:/app";
		
	}
	
}
