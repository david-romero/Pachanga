package com.p.model.metricas;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="request")
public class MetricaHttpRequest {

	@Id
	private String id;
	
	private String request;
	
	private Integer count;
	
	private Date fecha;
	
	public MetricaHttpRequest() {
		super();
	}

	public MetricaHttpRequest(String id, String request,Date fecha ,Integer count) {
		super();
		this.id = id;
		this.request = request;
		this.fecha = fecha;
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
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
