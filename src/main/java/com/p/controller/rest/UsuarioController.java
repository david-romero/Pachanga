package com.p.controller.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.p.controller.AbstractController;
import com.p.model.Comentario;
import com.p.model.Novedad;
import com.p.model.User;
import com.p.model.Voto;
import com.p.model.modelAux.EmailWrapper;
import com.p.model.modelAux.RegisterUser;
import com.p.service.ComentarioService;
import com.p.service.NovedadService;
import com.p.service.UsersService;
import com.p.service.VotoService;

@RestController
@RequestMapping(value = "/rest/usuarios")
public class UsuarioController extends AbstractController {

	@Autowired
	protected UsersService userService;
	
	@Autowired
	protected VotoService votoService;
	
	@Autowired
	protected NovedadService novedadService;
	
	@Autowired
	protected ComentarioService comentarioService;

	protected static final Logger log = Logger
			.getLogger(UsuarioController.class);

	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public List<User> inicio() {
		List<User> list = Lists.newArrayList();
		try {
			beginTransaction(true);
			list = Lists.newArrayList(userService.findAllDifferent(findUsernameUserSigned()));
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		return list;
	}

	@RequestMapping(value = "/editImage", method = RequestMethod.POST)
	public User save(Model model, @RequestParam("foto") MultipartFile file) {
		User usr = findUserSigned();
		try {
			beginTransaction();
			usr.setImagen(file.getBytes());
			usr.setTieneAvatar(true);
			usr = userService.save(usr);
			txStatus.flush();
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		return usr;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public User create(Model model, @RequestBody RegisterUser user) {
		User newUser = null;
		try {
			newUser = userService.map(user);
			beginTransaction();
			newUser = userService.save(newUser);
			txStatus.flush();
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		return newUser;
	}

	@RequestMapping(value = "/remember", method = RequestMethod.POST)
	public void create(Model model, @RequestBody EmailWrapper emailWrapper) {
		User newUser = null;
		try {
			beginTransaction(true);
			newUser = userService.getByEmail(emailWrapper.getEmail());
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		Assert.notNull(newUser);
		try {
			beginTransaction();
			userService.regenerarPassword(newUser);
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
	}

	@RequestMapping(value = "/find/{texto}", method = RequestMethod.GET)
	public List<User> find(@PathVariable(value = "texto") String texto) {
		List<User> list = Lists.newArrayList();
		try {
			beginTransaction(true);
			list.addAll(userService.find(texto));
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		return list;
	}

	@RequestMapping(value = "/votar/{idUsuario}", method = RequestMethod.POST)
	public ResponseEntity<Voto> votar(Model model, @RequestBody Voto voto, @PathVariable("idUsuario") Integer idUsuario) {
		ResponseEntity<Voto> response = new ResponseEntity<Voto>(voto,HttpStatus.CONTINUE);
		User emisor = findUserSigned();
		User receptor = findUser(idUsuario);
		Assert.isTrue(!emisor.equals(receptor));
		try {
			beginTransaction();
			voto = votoService.votar(voto,emisor,receptor);
			commitTransaction();
			response = new ResponseEntity<Voto>(voto,HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			log.error(e);
			rollbackTransaction();
			response = new ResponseEntity<Voto>(voto,HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
			response = new ResponseEntity<Voto>(voto,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	
	
	@RequestMapping(value = "/getMediaVotos/{idUsuario}", method = RequestMethod.GET)
	public Integer getMediaVotos(@PathVariable("idUsuario") Integer idUsuario){
		User usrReceptor = findUser(idUsuario);
		Integer media = 0;
		try {
			beginTransaction(true);
			media = votoService.calcularMedia(usrReceptor);
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		return media;
	}
	
	@RequestMapping(value = "/getVotos/{idUsuario}", method = RequestMethod.GET)
	public Map<Integer,Integer> getVotos(@PathVariable("idUsuario") Integer idUsuario){
		User usrReceptor = findUser(idUsuario);
		 Map<Integer,Integer> votos = Maps.newHashMap();
		try {
			beginTransaction(true);
			votos = votoService.getVotos(usrReceptor);
			commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			rollbackTransaction();
		}
		return votos;
	}
	
	@RequestMapping(value = "/getNovedades/{idUsuario}/{pagina}", method = RequestMethod.GET)
	public Page<Novedad> getNovedades(@PathVariable("idUsuario") Integer idUsuario,
			@PathVariable("pagina") Integer pagina){
		User usrReceptor = findUser(idUsuario);
		Page<Novedad> novedades = null;
		try {
			beginTransaction(true);
			novedades = novedadService.getNovedades(usrReceptor,pagina);
			commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			rollbackTransaction();
		}
		return novedades;
	}
	
	@RequestMapping(value = "/novedad/create/{idUsuario}", method = RequestMethod.POST)
	public Novedad createNovedad(@PathVariable("idUsuario") Integer idUsuario,
			@RequestBody Novedad novedad){
		User usrReceptor = findUser(idUsuario);
		User usrEmisor = findUserSigned();
		try {
			beginTransaction();
			novedad = novedadService.create(usrReceptor,usrEmisor,novedad);
			commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			rollbackTransaction();
		}
		return novedad;
	}
	
	@RequestMapping(value = "/novedad/like/{idNovedad}", method = RequestMethod.GET)
	public Novedad upLike(@PathVariable("idNovedad") Integer idNovedad){
		User usrEmisor = findUserSigned();
		Assert.notNull(usrEmisor);
		Novedad novedad = null;
		try {
			beginTransaction();
			novedad = novedadService.findOne(idNovedad);
			novedad.setLikes(novedad.getLikes()+1);
			novedad = novedadService.save(novedad);
			commitTransaction();
		} catch (Exception e) {
			log.error(e);
			rollbackTransaction();
		}
		return novedad;
	}
	
	@RequestMapping(value = "/novedad/comentario/create/{idNovedad}", method = RequestMethod.POST)
	public Novedad createComentario(@PathVariable("idNovedad") Integer idNovedad,
			@RequestBody Comentario comentario){
		User usrEmisor = findUserSigned();
		Novedad novedad = null;
		try {
			beginTransaction(true);
			novedad = novedadService.findOne(idNovedad);
			commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			rollbackTransaction();
		}
		try {
			beginTransaction();
			comentario = comentarioService.save(comentario,novedad,usrEmisor);
			commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			rollbackTransaction();
		}
		try {
			beginTransaction();
			novedad.getComentarios().add(comentario);
			novedad = novedadService.save(novedad);
			commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			rollbackTransaction();
		}
		Stream<Comentario> whitoutDuplicates = novedad.getComentarios().stream().distinct();
		novedad.setComentarios(whitoutDuplicates.sorted( (e1 ,e2) ->{
			//DESC e2.compare.e1
			//ASC e1.compare.e2
			return e2.getFecha().compareTo(e1.getFecha());
		}).collect(Collectors.toList()));
		return novedad;
	}
	
	/**
	 * @param idUsuario
	 * @param receptor
	 * @return
	 */
	protected User findUser(Integer idUsuario) {
		User user = null;
		try {
			beginTransaction(true);
			user = userService.findOne(idUsuario);
			commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			rollbackTransaction();
		}
		return user;
	}

}
