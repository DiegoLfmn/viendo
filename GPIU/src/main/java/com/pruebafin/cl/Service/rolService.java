package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.rolEntity;
import com.pruebafin.cl.Repository.rolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class rolService {
    private rolRepository rolRepo;

    @Autowired
    public rolService(rolRepository rolRepo) {
        this.rolRepo = rolRepo;
    }

    public rolEntity guardarRol(rolEntity rol){
        return rolRepo.save(rol);
    }

    public List<rolEntity> obtenerRoles(){
        return rolRepo.findAll();
    }

    public Optional<rolEntity> obtenerRolPorId(Long id){
        return rolRepo.findById(id);
    }

    public rolEntity actualizarRol(rolEntity rol){
        return rolRepo.save(rol);
    }

    public void eliminarRol(Long id){
        rolRepo.deleteById(id);
    }

    public List<rolEntity> obtenerRolPorNombre(String nombre){
        return rolRepo.findByNombreRol(nombre);
    }
}
