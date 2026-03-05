package com.pruebafin.cl.Repository;


import com.pruebafin.cl.Entity.contenidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface contenidoRepository extends JpaRepository<contenidoEntity, Long> {
    List<contenidoEntity> findByTitulo(String titulo);

    List<contenidoEntity> findByTipo(String tipo);
<<<<<<< HEAD
}
=======

    List<contenidoEntity> findByEstado(String estado);
}
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
