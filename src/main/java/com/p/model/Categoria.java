package com.p.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
@Entity
public class Categoria extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7724003014847557531L;
	

	
	
	protected String titulo;
	@NotNull
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
