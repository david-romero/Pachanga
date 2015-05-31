package com.p.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Notificacion extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1448120295812757712L;
	@NotNull
	private String titulo;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	
}
