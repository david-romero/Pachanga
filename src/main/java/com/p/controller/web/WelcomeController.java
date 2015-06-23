package com.p.controller.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.p.controller.AbstractController;
import com.p.model.Role;
import com.p.model.User;
import com.p.model.modelAux.Pair;
import com.p.service.MetricService;
import com.p.service.UsersService;

@Controller
@Transactional()
@RequestMapping(value="/")
public class WelcomeController extends AbstractController{

	@Resource(name = "usersService")
	private UsersService usersService;
	
	@Autowired
	private MetricService metricService; 
	
	@RequestMapping(value="/app",method = RequestMethod.GET, headers = "Accept=application/json")
	@Transactional
	public String indexApp(Model model){
		User usr = new User();
		Map<Integer,Integer> gruposTam = Maps.newHashMap();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			usr.setEmail(userSigned.getUsername());
			try {
				beginTransaction(true);
				usr = usersService.getByEmail(userSigned.getUsername());
				Hibernate.initialize(usr.getGrupos());
				usr.getGrupos().forEach(grupo->{
					Hibernate.initialize(grupo.getUsuarios());
					gruposTam.put(grupo.getId(), grupo.getUsuarios().size() );
				});
				commitTransaction();
			} catch (Exception e) {
				rollbackTransaction();
				model.addAttribute("errorweb", e);
			}
			if ( usr.getRole().equals(Role.ROLE_ADMIN) ){
				Pair<Integer,Integer> stadistics = null;
				Integer siteVisitors = null;
				Integer peticiones = null;
				try{
					stadistics = metricService.getSystemStadistics();
					siteVisitors = metricService.siteVisitors();
					peticiones = metricService.peticiones();
				}catch( Exception e ){
					//Capturamos cualquier exception. Estadisticas son secundarias
					stadistics = Pair.create(0, 0);
					siteVisitors = 0;
					peticiones = 0;
				}
				model.addAttribute("mobilePorcentaje", stadistics.getFirst());
				model.addAttribute("desktopPorcentaje", stadistics.getSecond());
				model.addAttribute("siteVisitors", siteVisitors);
				model.addAttribute("peticiones", peticiones);
			}
		}else{
			usr.setEmail("anonymous@anonymous.com");
			usr.setId(0);
		}
		
		
		model.addAttribute("gruposUsrSigned", usr.getGrupos());
		model.addAttribute("tamGruposUsrSigned", gruposTam);
		model.addAttribute("userSigned", usr);
		return "index";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public String index(Model model){
		return "redirect:/app";
		
	}
	
}
