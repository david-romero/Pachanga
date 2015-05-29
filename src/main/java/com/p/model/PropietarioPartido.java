/**
 * 
 */
package com.p.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
/**
 * @author David
 *
 */
public abstract class PropietarioPartido extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8072071724128480006L;


	public abstract String getEmail();
	
	
	public abstract Collection<Partido> getPartidosCreados();
	
}
