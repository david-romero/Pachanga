package com.p.model.metricas;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection="email")
public class MetricaEmail {

	@Id
	private String id;
	
	private boolean enviado;
	@DateTimeFormat(iso = ISO.DATE)
	private Date fecha;
	
	private Integer count;
	
	public MetricaEmail() {
		super();
	}

	public MetricaEmail(String id, boolean enviado, Date fecha, Integer count) {
		super();
		this.id = id;
		this.enviado = enviado;
		this.fecha = fecha;
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isEnviado() {
		return enviado;
	}

	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
