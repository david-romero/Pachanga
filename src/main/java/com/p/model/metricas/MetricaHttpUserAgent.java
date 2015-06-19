package com.p.model.metricas;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userAgent")
public class MetricaHttpUserAgent {

	@Id
	private String id;

	private String os;

	private String browser;

	private Integer count;

	public MetricaHttpUserAgent() {
		super();
	}

	public MetricaHttpUserAgent(String id, String os, String browser,
			Integer count) {
		super();
		this.id = id;
		this.os = os;
		this.browser = browser;
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

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

}
