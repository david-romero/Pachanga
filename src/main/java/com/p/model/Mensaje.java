package com.p.model;

import java.util.Date;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Mensaje extends BaseEntity{
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
	private User receptor;
	@NotNull
	private String contenido;
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date fecha;
	
	@Valid
	@ManyToOne(optional=true)
	private Grupo propietario;
	
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
	
	@JsonProperty
	public String getFechaRepresentacion() {
		PrettyTime p = new PrettyTime(new Locale("ES","es"));
		fechaRepresentacion = p.format(fecha);
		return fechaRepresentacion;
	}

	public Grupo getPropietario() {
		return propietario;
	}

	public void setPropietario(Grupo propietario) {
		this.propietario = propietario;
	}

	
	
}
