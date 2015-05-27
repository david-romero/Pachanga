package com.p.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Categoria extends BaseEntity<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3472798802254577100L;
	@NotNull
	private String titulo;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}
