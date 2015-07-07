/**
 * 
 */
package com.p.model;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.SortNatural;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;

@Entity
/**
 * @author David
 *
 */
public class Novedad extends BaseEntity {

	public Novedad() {
		this.comentarios = Sets.newHashSet();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7779409779051940598L;

	@NotNull
	@Valid
	@ManyToOne()
	protected User usuario;

	@Valid
	@ManyToOne(optional = true)
	protected User emisor;

	@Valid
	@ManyToOne(optional = true)
	protected Partido partido;

	@Valid
	@ManyToOne(optional = true)
	protected Grupo grupo;

	@NotNull
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "novedad", cascade = { CascadeType.REMOVE })
	@SortNatural
	@OrderBy(clause = "FECHA DESC")
	@Valid
	@JsonInclude(JsonInclude.Include.ALWAYS)
	protected Collection<Comentario> comentarios;

	@NotNull
	private String contenido;
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date fecha;
	@NotNull
	@Min(0)
	private Integer likes;

	@Enumerated(EnumType.STRING)
	@NotNull
	private NovedadType tipo;

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public User getEmisor() {
		return emisor;
	}

	public void setEmisor(User emisor) {
		this.emisor = emisor;
	}

	public Collection<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Collection<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Transient
	@JsonProperty("fechaRepresentacion")
	private String fechaRepresentacion;

	public String getFechaRepresentacion() {
		PrettyTime p = new PrettyTime(new Locale("ES", "es"));
		fechaRepresentacion = p.format(fecha);
		return fechaRepresentacion;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public NovedadType getTipo() {
		return tipo;
	}

	public void setTipo(NovedadType tipo) {
		this.tipo = tipo;
	}

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public enum NovedadType {
		NOTICIA, PARTIDO, GRUPO
	}

}
