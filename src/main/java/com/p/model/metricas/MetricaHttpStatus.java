package com.p.model.metricas;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="metricas")
public class MetricaHttpStatus {

	
	@Id
	private String id;
	
	private Integer status;
	
	private Date fecha;
	
	private Integer count;
	
	

	public MetricaHttpStatus(String id, Integer status,Date fecha, Integer count) {
		super();
		this.id = id;
		this.status = status;
		this.fecha = fecha;
		this.count = count;
	}
	
	

	public MetricaHttpStatus() {
		super();
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
