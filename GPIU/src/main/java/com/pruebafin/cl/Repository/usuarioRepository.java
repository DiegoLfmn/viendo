package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.usuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface usuarioRepository extends JpaRepository<usuarioEntity, Long> {

    @Query("SELECT u FROM usuarioEntity u WHERE u.correoUsuario = :correo")
    Optional<usuarioEntity> findByCorreoUsuario(@Param("correo") String correo);

    List<usuarioEntity> findByNombreUsuario(String nombreUsuario);


}
