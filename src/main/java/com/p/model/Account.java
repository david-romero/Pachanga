package com.p.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Account {

	private String reference;
	private String label;
	private String address;
	private String type;
	private String messagesremaining;
	private String expireson;
	private String role;
	
	public Account() {
		super();
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public String getMessagesremaining() {
		return messagesremaining;
	}
	
	public void setMessagesremaining(String messagesremaining) {
		this.messagesremaining = messagesremaining;
	}
	
	public String getExpireson() {
		return expireson;
	}
	
	public void setExpireson(String expireson) {
		this.expireson = expireson;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

}
