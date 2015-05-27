package com.p.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;

import com.google.common.collect.Sets;
@Entity
public class Grupo extends BaseEntity<Long>{

	public Grupo(){
		usuarios = Sets.newHashSet();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5617492744121518674L;
	@ManyToMany(mappedBy="grupos")
	@Valid
	protected Set<User> usuarios;
	
	public Set<User> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Set<User> usuarios) {
		this.usuarios = usuarios;
	}
	
	
	
}
