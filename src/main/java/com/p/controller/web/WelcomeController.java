package com.p.controller.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.p.controller.AbstractController;
import com.p.model.Role;
import com.p.model.User;
import com.p.model.metricas.MetricaPartidoUsuario;
import com.p.model.modelAux.Pair;
import com.p.service.MetricService;
import com.p.service.MetricStadisticsService;
import com.p.service.UsersService;

@Controller
@Transactional()
@RequestMapping(value="/")
public class WelcomeController extends AbstractController{

	@Resource(name = "usersService")
	private UsersService usersService;
	
	@Autowired
	private MetricService metricService; 
	
	@Autowired
	private MetricStadisticsService metricStadisticsService; 
	
	/**
	 * Log
	 */
	private static final Logger LOGGER = Logger
			.getLogger(WelcomeController.class);
	
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
				generateStadistics(model);
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

	/**
	 * @param model
	 */
	protected void generateStadistics(Model model) {
		Pair<Integer,Integer> stadistics = null;
		Integer siteVisitors = null;
		Integer peticiones = null;
		Integer paginas = null;
		Integer siteTraffic = null;
		List<Integer> listVisitantes = null;
		List<Integer> listRequest = null;
		List<Integer> listLogin = null;
		Integer usuariosActivos = null;
		Integer errores = null;
		Integer partidos = null;
		Integer mensajes = null;
		List<Integer> listErrores = null;
		List<Integer> listPartidos = null;
		List<Integer> listMensajes = null;
		List<MetricaPartidoUsuario> partidosUsuarios = null;
		try{
			stadistics = metricStadisticsService.getSystemStadistics();
			siteVisitors = metricStadisticsService.siteVisitors();
			peticiones = metricStadisticsService.peticiones();
			paginas = metricStadisticsService.paginas();
			listVisitantes = metricStadisticsService.listSiteVisitors();
			listRequest = metricStadisticsService.listRequest();
			siteTraffic = metricStadisticsService.siteTraffict();
			listLogin = metricStadisticsService.listLogin();
			usuariosActivos = metricStadisticsService.usuariosActivos();
			errores = metricStadisticsService.errores();
			listErrores = metricStadisticsService.listErrores();
			partidos = metricStadisticsService.partidos();
			listPartidos = metricStadisticsService.listPartidos();
			mensajes = metricStadisticsService.mensajes();
			listMensajes = metricStadisticsService.listMensajes();
			partidosUsuarios = metricStadisticsService.rankingUsuariosPartidos();
		}catch( Exception e ){
			//Capturamos cualquier exception. Estadisticas son secundarias
			stadistics = Pair.create(0, 0);
			siteVisitors = 0;
			peticiones = 0;
			paginas = 0;
			listVisitantes = Lists.newArrayList();
			listRequest = Lists.newArrayList();
			siteTraffic = 0;
			listLogin = Lists.newArrayList();
			usuariosActivos = 0;
			errores = 0;
			listErrores = Lists.newArrayList();
			listPartidos = Lists.newArrayList();
			partidos = 0;
			listMensajes = Lists.newArrayList();
			mensajes = 0;
			partidosUsuarios = Lists.newArrayList();
		}
		model.addAttribute("mobilePorcentaje", stadistics.getFirst());
		model.addAttribute("desktopPorcentaje", stadistics.getSecond());
		model.addAttribute("siteVisitors", siteVisitors);
		model.addAttribute("peticiones", peticiones);
		model.addAttribute("paginas", paginas);
		model.addAttribute("listSiteVisitors", listVisitantes );
		model.addAttribute("listRequest", listRequest );
		model.addAttribute("siteTraffic", siteTraffic );
		model.addAttribute("listLogin", listLogin );
		model.addAttribute("usuariosActivos", usuariosActivos );
		model.addAttribute("errores", errores );
		model.addAttribute("listErrores", listErrores );
		model.addAttribute("partidos", partidos );
		model.addAttribute("listPartidos", listPartidos );
		model.addAttribute("mensajes", mensajes );
		model.addAttribute("listMensajes", listMensajes );
		model.addAttribute("partidosUsuarios", partidosUsuarios );
	}
	
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public String index(Model model){
		return "redirect:/app";
	}
	
	@ExceptionHandler(TransactionSystemException.class)
    public ModelAndView handleNullPointerException(HttpServletRequest request, Exception ex){
		LOGGER.error("Requested URL="+request.getRequestURL());
		LOGGER.error("Exception Raised="+ex);
         
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        
        return modelAndView;
    }
	
}
