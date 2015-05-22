package com.p.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

@RestController
@RequestMapping(value = "/rest")
public class PartidoController {
	
	static int cursor = 1;

	@RequestMapping(value = "/inicio",method = RequestMethod.GET)
    public List<Object> inicio() {
		List<Object> objetos = Lists.newArrayList();
		objetos.add(cursor);
		cursor++;
		objetos.add(cursor);
		cursor++;
		objetos.add(cursor);
		cursor++;
		objetos.add(cursor);
		cursor++;
		objetos.add(cursor);
		cursor++;
        return objetos;
    }
     
    @RequestMapping(value = "/loadMorePartidos",method = RequestMethod.GET)
    public List<Object> getMorePartidos() {
    	List<Object> objetos = Lists.newArrayList();
		objetos.add(cursor);
		cursor++;
		objetos.add(cursor);
		cursor++;
		objetos.add(cursor);
		cursor++;
		objetos.add(cursor);
		cursor++;
		objetos.add(cursor);
		cursor++;
        return objetos;
    }
	
}
