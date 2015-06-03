package com.p.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.p.model.Grupo;
@Repository
public interface GrupoRepository extends CrudRepository<Grupo, Integer>{

}
