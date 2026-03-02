package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.puntointeresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface puntointeresRepository extends JpaRepository<puntointeresEntity, Long> {
//    List<puntointeresEntity> findByNombre(String nombre);
}
