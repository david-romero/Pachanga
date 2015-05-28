package com.p.model;

import java.util.Date;
import java.util.Locale;

import javax.persistence.Transient;

import org.ocpsoft.prettytime.PrettyTime;

public class Mensaje {

	private User emisor;
	
	private User receptor;
	
	private String contenido;
	
	private Date fecha;
	
	private boolean leido;
	
	@Transient
	private String fechaRepresentacion;

	public User getEmisor() {
		return emisor;
	}

	public void setEmisor(User emisor) {
		this.emisor = emisor;
	}

	public User getReceptor() {
		return receptor;
	}

	public void setReceptor(User receptor) {
		this.receptor = receptor;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	public String getFechaRepresentacion() {
		PrettyTime p = new PrettyTime(new Locale("ES","es"));
		fechaRepresentacion = p.format(fecha);
		return fechaRepresentacion;
	}

	
	
}
