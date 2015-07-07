package com.p.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.p.service.SocialMediaService;

@Entity
@Table(name = "user_accounts")
public class User extends PropietarioPartido {
 
	public User() {
		partidosJugados = Sets.newHashSet();
		grupos = Sets.newHashSet();
    }
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4183055938551792532L;
 
    @Column(name = "first_name", length = 100,nullable = false)
    private String firstName;
 
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;
 
    @Column(name = "password", length = 255)
    private String password;
 
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private Role role;
 
    @Enumerated(EnumType.STRING)
    @Column(name = "sign_in_provider", length = 20)
    private SocialMediaService signInProvider;
    
    @Lob
	@Column(name = "imagen")
    /**
	 * avatar
	 */
	private byte[] imagen;
    
    @Column(name = "karma_points")
    private Integer[] karmaPoints = new Integer[]{0,0,0,0,0};
    
    @ManyToMany()
	@Valid
	@NotNull
	@JsonIgnore
	private Set<Grupo> grupos;
    
    @NotNull
	@Valid
	@ManyToMany()
    private Collection<Partido> partidosJugados;
    
    
    @JsonIgnore
    public Collection<Partido> getPartidosJugados() {
		return partidosJugados;
	}

	public void setPartidosJugados(Collection<Partido> partidosJugados) {
		this.partidosJugados = partidosJugados;
	}

	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}

	@Transient
	@JsonProperty(value="tieneAvatar")
    private boolean tieneAvatar;
    
    @Transient
    private float karma;
    
    @Transient
    @JsonProperty(value="screenName")
    private String screenName;
    
    @NotNull
    private String avatar;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer[] getKarmaPoints() {
		return karmaPoints;
	}

	public void setKarmaPoints(Integer[] karmaPoints) {
		this.karmaPoints = karmaPoints;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public SocialMediaService getSignInProvider() {
		return signInProvider;
	}

	public void setSignInProvider(SocialMediaService signInProvider) {
		this.signInProvider = signInProvider;
	}
    
	@JsonIgnore
	/**
	 * @return imagen
	 */
	public byte[] getImagen() {
		if (this.imagen != null) {
			return imagen.clone();
		} else {
			return new byte[0];
		}
	}

	/**
	 * @param imagen
	 *            the imagen to set Establecer el imagen
	 */
	public void setImagen(final byte[] imagen) {
		if (imagen == null) {
			this.imagen = new byte[0];
			setTieneAvatar(false);
		} else {
			this.imagen = Arrays.copyOf(imagen, imagen.length);
			setTieneAvatar(true);
		}
	}

	public boolean isTieneAvatar() {
		this.tieneAvatar = getImagen().length > 0;
		return this.tieneAvatar;
	}

	public void setTieneAvatar(boolean tieneAvatar) {
		this.tieneAvatar = tieneAvatar;
	}

	public float getKarma() {
		
		return karma;
	}
	
	public String getScreenName(){
		StringBuilder builder = new StringBuilder();
		if ( getFirstName() != null && !getFirstName().isEmpty() ){
			builder.append(getFirstName());
			if ( getLastName() != null && !getLastName().isEmpty() ){
				builder.append(" ");
				builder.append(getLastName());
			}
		}else{
			builder.append(getEmail().split("@")[0]);
		}
		builder.append(" ");
		return builder.toString();
	}

	@Transient
	public static String[] avatarCss = {
	        "bgm-red",
	        "bgm-white",
	        "bgm-black",
	        "bgm-brown",
	        "bgm-pink",
	        "bgm-blue",
	        "bgm-purple",
	        "bgm-deeppurple",
	        "bgm-lightblue",
	        "bgm-cyan",
	        "bgm-teal",
	        "bgm-green",
	        "bgm-lightgreen",
	        "bgm-lime",
	        "bgm-yellow",
	        "bgm-amber",
	        "bgm-orange",
	        "bgm-deeporange",
	        "bgm-gray",
	        "bgm-bluegray",
	        "bgm-indigo"
	      };
    

}
