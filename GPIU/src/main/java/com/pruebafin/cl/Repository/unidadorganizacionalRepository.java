package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.unidadorganizacionalEntity;
import com.pruebafin.cl.Entity.usuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface unidadorganizacionalRepository extends JpaRepository<unidadorganizacionalEntity, Long> {
//    List<unidadorganizacionalEntity> findByNombre(String nombre);

}
