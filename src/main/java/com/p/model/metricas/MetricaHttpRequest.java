package com.p.model.metricas;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="request")
public class MetricaHttpRequest {

	@Id
	private String id;
	
	private String request;
	
	private Integer count;
	
	public MetricaHttpRequest() {
		super();
	}

	public MetricaHttpRequest(String id, String request, Integer count) {
		super();
		this.id = id;
		this.request = request;
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
	
	
	
}
