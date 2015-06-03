package com.p.controller.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.p.controller.AbstractController;
import com.p.model.Grupo;
import com.p.model.Partido;
import com.p.service.GrupoService;
import com.p.service.UsersService;

@RestController
@RequestMapping(value = "/rest/comunidad")
public class ComunidadController extends AbstractController{
	
	@Autowired
	protected GrupoService service;
	
	@Autowired
	protected UsersService userService;

	@RequestMapping(value = "/partidos/{idComunidad}", method = RequestMethod.GET)
	public Collection<Partido> inicio(@PathVariable(value = "idComunidad") Integer idComunidad) {
		Grupo grp = null;
		try{
			beginTransaction();
			grp = service.findOne(idComunidad);
			commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();
		}
		Assert.notNull(grp);
		return grp.getPartidosCreados();
	}
	
	@RequestMapping(value = "/editImage/{idComunidad}",method = RequestMethod.POST)
	public Grupo save(Model model,@RequestParam("foto") MultipartFile file,
			@PathVariable(value = "idComunidad") Integer idComunidad) {
		Grupo grp = null;
		try{
			beginTransaction();
			grp = service.findOne(idComunidad);
			commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();
		}
		Assert.notNull(grp);
		try{
			beginTransaction();
			grp.setImagen(file.getBytes());
			grp = service.save(grp);
			txStatus.flush();
			commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();
		}
		return grp;
	}
	
	@RequestMapping(value = "/getImage/{id}")
	public void getGrupoImage(HttpServletResponse response,
			@PathVariable("id") Integer id) throws IOException {

		response.setContentType("image/jpeg");
		byte[] buffer = null;
		Grupo grp = null;
		try{
			beginTransaction();
			grp = service.findOne(id);
			commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();
		}
		if ( grp.getImagen() == null || grp.getImagen().length == 0) {
			InputStream in = this.getClass().getResourceAsStream("/profile-pic-300px.jpg");
			buffer = IOUtils.toByteArray(in);
		} else {
			buffer = grp.getImagen();
		}
		InputStream in1 = new ByteArrayInputStream(buffer);
		IOUtils.copy(in1, response.getOutputStream());
	}
	
}
