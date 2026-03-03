package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.sistemaexternoEntity;
import com.pruebafin.cl.Entity.usuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface sistemaexternoRepository extends JpaRepository<sistemaexternoEntity, Long> {
    List<sistemaexternoEntity> findByNombreSistext(String nombreSistext);
}
