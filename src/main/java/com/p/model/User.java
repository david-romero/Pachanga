package com.p.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.p.service.SocialMediaService;

@Entity
@Table(name = "user_accounts")
public class User extends BaseEntity<Long> {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -4183055938551792532L;
 
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
 
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
    
    @Transient
    private boolean tieneAvatar;
    
    
    
    
 
    public User() {
 
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
    

}
