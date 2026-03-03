package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.piuEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface piuRepository extends JpaRepository<piuEntity, Long> {
    List<piuEntity> findByfechaInstalacion(Date fecha);

    List<piuEntity> findByEstadoPiu(String estado);
}
