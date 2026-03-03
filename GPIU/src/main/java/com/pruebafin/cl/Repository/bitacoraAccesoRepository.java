package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.bitacoraAccesoEntity;
import com.pruebafin.cl.Entity.piuEntity;
import com.pruebafin.cl.Entity.usuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface bitacoraAccesoRepository extends JpaRepository<bitacoraAccesoEntity, Long> {
    List<bitacoraAccesoEntity> findByusuario(usuarioEntity usuario);
    List<bitacoraAccesoEntity> findBypiuDeBita(piuEntity piu);

}
