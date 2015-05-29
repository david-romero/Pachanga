package com.p.model;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.ocpsoft.prettytime.PrettyTime;
@Entity
public class Partido extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -181161949409988033L;
	@NotNull
	@Valid
	@OneToMany()
	protected Collection<User> jugadores;

	protected Date fecha;

	protected String titulo;

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
	@NotNull
	@Valid
	@ManyToOne(optional=false,targetEntity=PropietarioPartido.class)
	private PropietarioPartido propietario;
	@NotNull
	private boolean publico;
	
	@NotNull
	@Min(value=0)
	@Max(value=30)
	private Integer plazas;
	
	@Transient
	private String fechaRepresentacion;
	
	public String getFechaRepresentacion() {
		PrettyTime p = new PrettyTime(new Locale("ES","es"));
		fechaRepresentacion = p.format(fecha);
		return fechaRepresentacion;
	}

	public PropietarioPartido getPropietario() {
		return propietario;
	}

	public void setPropietario(PropietarioPartido propietario) {
		this.propietario = propietario;
	}
	
	public Collection<User> getJugadores() {
		return jugadores;
	}

	public void setJugadores(Collection<User> jugadores) {
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

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

	public Integer getPlazas() {
		return plazas;
	}

	public void setPlazas(Integer plazas) {
		this.plazas = plazas;
	}
	
	

}
