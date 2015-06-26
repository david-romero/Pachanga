package com.p.model.metricas;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection="partidoUsuario")
public class MetricaPartidoUsuario {

	@Id
	private String id;
	
	private String username;
	
	private String fullName;
	
	private Integer count;
	@DateTimeFormat(iso = ISO.DATE)
	private Date fecha;
	
	public MetricaPartidoUsuario() {
		super();
	}

	public MetricaPartidoUsuario(String id, String username, String fullName,
			Integer count, Date fecha) {
		super();
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.count = count;
		this.fecha = fecha;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
