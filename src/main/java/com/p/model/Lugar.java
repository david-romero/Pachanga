package com.p.model;

import javax.persistence.Entity;

import com.sun.istack.internal.NotNull;

@Entity
public class Lugar extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2956838711366687295L;

	protected double lat;
	
	protected double lng;
	@NotNull
	protected String titulo;

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	
}
