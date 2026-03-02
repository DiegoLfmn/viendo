package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.eventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface eventoRepository extends JpaRepository<eventoEntity,Long> {

}
