package com.p.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
public class Voto extends BaseEntity{
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
	@Min(value=1)
	@Max(value=5)
	private Integer valor;
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date fecha;
	

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


	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
	

}
