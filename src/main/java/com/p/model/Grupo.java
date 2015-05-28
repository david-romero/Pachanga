package com.p.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.google.common.collect.Sets;
@Entity
public class Grupo extends BaseEntity<Long>{

	public Grupo(){
		usuarios = Sets.newHashSet();
		comentarios = Sets.newHashSet();
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
	
	
	@NotNull
	@OneToMany(mappedBy = "grupo", cascade = { CascadeType.REMOVE })
	@Sort(type = SortType.NATURAL)
	@Valid
	protected Set<Comentario> comentarios;

	public Set<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	
}
