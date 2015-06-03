package com.p.model;

import java.util.Date;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Comentario extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6609980812110669425L;
	@NotNull
	@Valid
	@ManyToOne()
	private User emisor;
	@NotNull
	@Valid
	@ManyToOne()
	private Grupo grupo;
	@NotNull
	private String contenido;
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date fecha;
	@NotNull
	private boolean leido;
	
	@Transient
	private String fechaRepresentacion;

	public User getEmisor() {
		return emisor;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public void setEmisor(User emisor) {
		this.emisor = emisor;
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
