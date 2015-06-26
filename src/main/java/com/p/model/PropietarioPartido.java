/**
 * 
 */
package com.p.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SortNatural;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
/**
 * @author David
 *
 */
public class PropietarioPartido extends BaseEntity {

	public PropietarioPartido(){
		partidosCreados = Sets.newHashSet();
	}
	
	public PropietarioPartido(Integer id){
		setId(id);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8072071724128480006L;
	@NotNull
	protected String email;
	@NotNull
	@OneToMany(mappedBy = "propietario", cascade = { CascadeType.REMOVE })
	@SortNatural
	@Valid
	protected  Collection<Partido> partidosCreados;

	public String getEmail(){
		return email;
	}
	
	@JsonIgnore
	public Collection<Partido> getPartidosCreados(){
		return partidosCreados;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setPartidosCreados(Collection<Partido> partidosCreados) {
		this.partidosCreados = partidosCreados;
	}
	
	
	
}
