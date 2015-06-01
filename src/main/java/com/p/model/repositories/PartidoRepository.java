package com.p.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p.model.Partido;
@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long>{

}
