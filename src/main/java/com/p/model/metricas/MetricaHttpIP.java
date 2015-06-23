package com.p.model.metricas;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection="ip")
public class MetricaHttpIP {

	@Id
	private String id;
	
	private String ip;
	
	private Integer count;
	@DateTimeFormat(iso = ISO.DATE)
	private Date fecha;
	
	public MetricaHttpIP() {
		super();
	}

	public MetricaHttpIP(String id, String ip,Date fecha ,Integer count) {
		super();
		this.id = id;
		this.ip = ip;
		this.fecha = fecha;
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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
