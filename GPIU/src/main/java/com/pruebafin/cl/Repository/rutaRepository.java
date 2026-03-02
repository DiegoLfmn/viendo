package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.rutaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface rutaRepository extends JpaRepository<rutaEntity,Long> {

}
