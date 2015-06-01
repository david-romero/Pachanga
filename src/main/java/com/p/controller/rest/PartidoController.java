package com.p.controller.rest;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.p.model.Lugar;
import com.p.model.Partido;
import com.p.model.User;
import com.p.service.PartidoService;

@RestController
@RequestMapping(value = "/rest/partido")
@Transactional
public class PartidoController {

	static int cursor = 1;
	
	@Autowired
	protected PartidoService partidoService;

	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public List<Partido> inicio() {
		List<Partido> partidos = Lists.newArrayList();

		partidos.addAll(partidoService.findAll(1).getContent());

		return partidos;
	}

	@RequestMapping(value = "/loadMorePartidos/{pagina}", method = RequestMethod.GET)
	public List<Partido> getMorePartidos(@PathVariable(value = "pagina") Integer pagina) {
		List<Partido> partidos = Lists.newArrayList();
		Assert.isTrue(pagina > 1);
		partidos.addAll(partidoService.findAll(pagina).getContent());

		return partidos;
	}
	
	@RequestMapping(value = "/apuntarse/{idPartido}", method = RequestMethod.POST)
	public Partido apuntarse(@PathVariable(value = "idPartido") Integer idPartido) {
		Partido p = new Partido();
		Long lg = new Long(idPartido);
		p.setId(lg);
		
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		User userConversationSigned = new User();
		userConversationSigned.setEmail(userSigned.getUsername());
		userConversationSigned.setId(999L);
		
		p.setJugadores((List<User>) Lists.newArrayList(userConversationSigned));
		
		return p;
		
		
		
	}
	
	@RequestMapping(value = "/save",method = RequestMethod.POST, headers = "Accept=application/json")
	public Partido save(Model model) {
		Random rd = new Random();
		Partido p = partidoService.create();
		p.setId(new Long(rd.nextInt(150)+1));
		return p;
	}
	
	
	@RequestMapping(value = "/editImage/{idPartido}",method = RequestMethod.POST, headers = "Accept=application/json")
	public Partido save(Model model,@RequestParam("foto") MultipartFile file,
			@PathVariable(value = "idPartido") Integer idPartido) {
		Random rd = new Random();
		Partido p = partidoService.create();
		p.setId(new Long(idPartido));
		try {
			p.setImagen(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

}
