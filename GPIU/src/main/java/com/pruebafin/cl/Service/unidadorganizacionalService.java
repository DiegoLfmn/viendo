package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.unidadorganizacionalEntity;
import com.pruebafin.cl.Entity.usuarioEntity;
import com.pruebafin.cl.Repository.unidadorganizacionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class unidadorganizacionalService {
    private unidadorganizacionalRepository unidadRepo;

    @Autowired
    public unidadorganizacionalService(unidadorganizacionalRepository unidadRepo) {
        this.unidadRepo = unidadRepo;
    }

    public unidadorganizacionalEntity guardarUnidad(unidadorganizacionalEntity unidad) {
        return unidadRepo.save(unidad);
    }

    public List<unidadorganizacionalEntity> obtenerAllUnidad() {
        return unidadRepo.findAll();
    }

    public Optional<unidadorganizacionalEntity> obtenerUnidadPorId(Long id) {
        return unidadRepo.findById(id);
    }

    public void eliminarUnidad(Long id) {
        unidadRepo.deleteById(id);
    }
    public List<unidadorganizacionalEntity> obtenerByNombre(String nombre) {
        return unidadRepo.findByNombreUnidad(nombre);
    }

    public List<unidadorganizacionalEntity> obtenerByTipoUnidad(String unidad) {
        return unidadRepo.findByTipoUnidad(unidad);
    }

}
