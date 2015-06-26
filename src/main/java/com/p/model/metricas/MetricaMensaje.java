package com.p.model.metricas;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection="mensaje")
public class MetricaMensaje {

	@Id
	private String id;
	
	private Integer count;
	@DateTimeFormat(iso = ISO.DATE)
	private Date fecha;
	
	public MetricaMensaje() {
		super();
	}

	public MetricaMensaje(String id, Date fecha ,Integer count) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	
}
