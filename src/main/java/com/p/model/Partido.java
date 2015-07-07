package com.p.model;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Sets;
import com.p.infrastructure.CustomJsonDateDeserializer;
import com.p.infrastructure.CustomJsonDateSerializer;
@Entity
public class Partido extends BaseEntity{

	public Partido(){
		jugadores = Sets.newHashSet();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -181161949409988033L;
	@NotNull
	@Valid
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
	protected Collection<User> jugadores;
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	protected Date fecha;
	@NotNull
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
	@Valid
	@ManyToOne(optional=false)
	private Categoria categoria;
	
	@NotNull
	private boolean publico;
	
	@NotNull
	@Min(value=0)
	@Max(value=30)
	private Integer plazas;
	
	@NotNull
	@Min(value=0)
	@Max(value=30)
	private Integer plazasOcupadas;
	
	@Transient
	private String fechaRepresentacion;
	
	@Transient
	@JsonProperty(value="categoriaTitulo")
	private String categoriaTitulo;
	@JsonProperty(value="fechaRepresentacion")
	public String getFechaRepresentacion() {
		PrettyTime p = new PrettyTime(new Locale("ES","es"));
		fechaRepresentacion = p.format(getFecha());
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
		if (fecha != null){
			return fecha;
		}else{
			return new Date(System.currentTimeMillis());
		}
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
	@JsonIgnore
	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getUrlImagen() {
		if ( imagen == null || imagen.length == 0 ){
			int number = getId() % 4;
			number++;
			urlImagen = "/P/resources/imgs/partidos/futbol/" +number + ".jpg";
		}else{
			urlImagen = "/P/partido/getPartidoImage/" + getId();
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Integer getPlazasOcupadas() {
		return plazasOcupadas != null ? plazasOcupadas : 0;
	}

	public void setPlazasOcupadas(Integer plazasOcupadas) {
		this.plazasOcupadas = plazasOcupadas;
	}

	public String getCategoriaTitulo() {
		return categoriaTitulo;
	}

	public void setCategoriaTitulo(String categoriaTitulo) {
		this.categoriaTitulo = categoriaTitulo;
	}
	
	
	

}
