package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.campusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface campusRepository extends JpaRepository<campusEntity, Long> {

    // Spring Boot leerá esto y buscará la variable "nombre_campus" en tu Entity
    List<campusEntity> findBynombreCampus(String nombre);
}