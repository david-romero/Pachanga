package com.p.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LogOld implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1062774040376400278L;
	private String log_filename;
	private Integer log_size;
	private Integer log_count;

	public LogOld(String log_filename, Integer log_size, Integer log_count) {
		super();
		this.log_filename = log_filename;
		this.log_size = log_size;
		this.log_count = log_count;
	}

	public LogOld() {
		super();
	}

	public String getLog_filename() {
		return log_filename;
	}

	public void setLog_filename(String log_filename) {
		this.log_filename = log_filename;
	}

	public Integer getLog_size() {
		return log_size;
	}

	public void setLog_size(Integer log_size) {
		this.log_size = log_size;
	}

	public Integer getLog_count() {
		return log_count;
	}

	public void setLog_count(Integer log_count) {
		this.log_count = log_count;
	}
}
