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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


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
	@Valid
	@ManyToOne(optional=true)
	private Grupo grupo;
	@Valid
	@ManyToOne(optional=true)
	@JsonIgnore
	private Novedad novedad;
	@NotNull
	private String contenido;
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date fecha;
	@NotNull
	private boolean leido;

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

	@Transient
	@JsonProperty("fechaRepresentacion")
	private String fechaRepresentacion;

	public String getFechaRepresentacion() {
		PrettyTime p = new PrettyTime(new Locale("ES", "es"));
		fechaRepresentacion = p.format(fecha);
		return fechaRepresentacion;
	}
	
	public Novedad getNovedad() {
		return novedad;
	}
	public void setNovedad(Novedad novedad) {
		this.novedad = novedad;
	}

	
	
}
