package com.p.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity<T extends Serializable> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8948822482158960890L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
    private T id;
    
    @Version
    private Integer version;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    protected BaseEntity() {
    }

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
    
    


}