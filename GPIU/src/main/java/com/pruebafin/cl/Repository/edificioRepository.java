package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.edificioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface edificioRepository extends JpaRepository<edificioEntity, Long> {
    List<edificioEntity> findBynombreEdificio(String name);
}
