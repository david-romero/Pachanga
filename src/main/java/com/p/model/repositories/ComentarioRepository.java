/**
 * 
 */
package com.p.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.p.model.Comentario;

/**
 * @author David
 *
 */
public interface ComentarioRepository extends
		JpaRepository<Comentario, Integer> {

}
