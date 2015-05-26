package com.p.model;

import java.util.Date;
import java.util.Random;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Sets;
@Entity
public class Partido extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -181161949409988033L;
	
	public Partido(){
		jugadores = Sets.newHashSet();
	}
	@ManyToMany(mappedBy="partidos")
	@Valid
	protected Set<User> jugadores;
	@NotNull
	protected Date fecha;
	@NotNull
	protected String titulo;
	@NotNull
	protected Double precio;
	@NotNull
	@Valid
	@ManyToOne()
	protected Lugar lugar;

	@Lob
	@Column(name = "imagen")
	/**
	 * avatar
	 */
	private byte[] imagen;
	
	protected String urlImagen;
	
	public Set<User> getJugadores() {
		return jugadores;
	}

	public void setJugadores(Set<User> jugadores) {
		this.jugadores = jugadores;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getUrlImagen() {
		if ( imagen == null || imagen.length == 0 ){
			Random rd = new Random();
			urlImagen = "/P/resources/imgs/partidos/futbol/" + (rd.nextInt(4)+1)+".jpg";
		}else{
			urlImagen = "/P/usuarios/getUserImage/bent@test.com";
		}
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	
	

}
