package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.permisoEntity;
import com.pruebafin.cl.Repository.permisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class permisoService {
    private permisoRepository permisoRepo;

    @Autowired
    public permisoService(permisoRepository permisoRepo) {
        this.permisoRepo = permisoRepo;
    }

    public permisoEntity guardarPermiso(permisoEntity permiso){
        return permisoRepo.save(permiso);
    }

    public List<permisoEntity> obtenerAllPermisos(){
        return permisoRepo.findAll();
    }

    public Optional<permisoEntity> obtenerPermisosPorId(Long id){
        return permisoRepo.findById(id);
    }

    public permisoEntity actualizarPermiso(permisoEntity permiso){
        return permisoRepo.save(permiso);
    }

    public void eliminarPermisos(Long id){
        permisoRepo.deleteById(id);
    }


}
