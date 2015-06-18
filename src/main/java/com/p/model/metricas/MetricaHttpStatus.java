package com.p.model.metricas;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="metricas")
public class MetricaHttpStatus {

	
	@Id
	private String id;
	
	private Integer status;
	
	private Integer count;
	
	

	public MetricaHttpStatus(String id, Integer status, Integer count) {
		super();
		this.id = id;
		this.status = status;
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
}
