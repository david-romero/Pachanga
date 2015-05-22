package com.p.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Log implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1062774040376400278L;
	private String log_path;
	private String log_filename;
	private Integer log_size;
	private Integer log_count;
	private String event_log_path;
	private Integer event_log_count;
	private Integer event_log_size;
	private String ats_log_path;
	private Integer ats_log_size;
	private Integer ats_log_count;

	public Log(String log_path, Integer log_size, Integer log_count,
			String event_log_path, Integer event_log_count,
			Integer event_log_size) {
		super();
		this.log_path = log_path;
		this.log_size = log_size;
		this.log_count = log_count;
		this.event_log_path = event_log_path;
		this.event_log_count = event_log_count;
		this.event_log_size = event_log_size;
	}

	public Log() {
		super();
	}

	public String getLog_path() {
		return log_path;
	}

	public void setLog_path(String log_path) {
		this.log_path = log_path;
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

	public String getEvent_log_path() {
		return event_log_path;
	}

	public void setEvent_log_path(String event_log_path) {
		this.event_log_path = event_log_path;
	}

	public Integer getEvent_log_count() {
		return event_log_count;
	}

	public void setEvent_log_count(Integer event_log_count) {
		this.event_log_count = event_log_count;
	}

	public Integer getEvent_log_size() {
		return event_log_size;
	}

	public void setEvent_log_size(Integer event_log_size) {
		this.event_log_size = event_log_size;
	}

	public String getAts_log_path() {
		return ats_log_path;
	}

	public void setAts_log_path(String ats_log_path) {
		this.ats_log_path = ats_log_path;
	}

	public Integer getAts_log_size() {
		return ats_log_size;
	}

	public void setAts_log_size(Integer ats_log_size) {
		this.ats_log_size = ats_log_size;
	}

	public Integer getAts_log_count() {
		return ats_log_count;
	}

	public void setAts_log_count(Integer ats_log_count) {
		this.ats_log_count = ats_log_count;
	}

	
}
