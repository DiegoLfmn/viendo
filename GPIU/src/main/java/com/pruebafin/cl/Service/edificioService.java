package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.edificioEntity;
import com.pruebafin.cl.Repository.edificioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class edificioService {
    private edificioRepository edificioRepo;

    @Autowired
    public edificioService(edificioRepository edificioRepo) {
        this.edificioRepo = edificioRepo;
    }

    /// guardar + nombre
    public edificioEntity guardarEdificio(edificioEntity edificio) {
        return edificioRepo.save(edificio);
    }
    /// obtener todos los + nombre
    public List<edificioEntity> obtenerAllEdificios() {
        return edificioRepo.findAll();
    }

    /// obtener por id
    public Optional<edificioEntity> obtenerEdificioPorId(Long id) {
        return edificioRepo.findById(id);
    }

    /// actualizar + nombre
    public edificioEntity actualizarEdificio(edificioEntity edificio) {
        return edificioRepo.save(edificio);
    }

    ///  eliminar
    public void eliminarEdificioPorId(Long id) {
        edificioRepo.deleteById(id);
    }
    //si se agregoi algo al repository
    public List<edificioEntity> buscarEdificioPorNombre(String nombre) {
        return edificioRepo.findBynombreEdificio(nombre);
    }
}
