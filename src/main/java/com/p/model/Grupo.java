package com.p.model;

import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.format.annotation.DateTimeFormat;
@Entity
public class Grupo extends PropietarioPartido{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5617492744121518674L;
	
	private String titulo;
	
	@Lob
	@Column(name = "imagen")
    /**
	 * avatar
	 */
	private byte[] imagen;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	protected Date fechaCreacion;
	
	@Transient
	private String fechaRepresentacion;
	

	@ManyToMany(mappedBy="grupos")
	@Valid
	protected Set<User> usuarios;
	
	
	
	
	@NotNull
	@OneToMany(mappedBy = "grupo", cascade = { CascadeType.REMOVE })
	@Sort(type = SortType.NATURAL)
	@Valid
	protected Set<Comentario> comentarios;
	
	@Transient
	protected Integer size;
	

	public Set<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	public Set<User> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Set<User> usuarios) {
		this.usuarios = usuarios;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public String getFechaRepresentacion() {
		PrettyTime p = new PrettyTime(new Locale("ES","es"));
		fechaRepresentacion = p.format(fechaCreacion);
		return fechaRepresentacion;
	}
	
	public Integer getSize() {
		return usuarios.size();
	}

}
