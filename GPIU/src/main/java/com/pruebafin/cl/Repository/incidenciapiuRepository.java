package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.incidenciapiuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface incidenciapiuRepository extends JpaRepository<incidenciapiuEntity, Long> {

}
