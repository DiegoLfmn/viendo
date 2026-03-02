package com.pruebafin.cl.Repository;
import com.pruebafin.cl.Entity.usuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface usuarioRepository extends JpaRepository<usuarioEntity, Long> {
//    List<usuarioEntity> findByNombre(String nombre);
//
//    List<usuarioEntity> findByCorreo(String correo);
}
