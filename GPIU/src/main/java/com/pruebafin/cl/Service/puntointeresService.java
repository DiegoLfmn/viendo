package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.puntointeresEntity;
import com.pruebafin.cl.Repository.puntointeresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class puntointeresService {
    private puntointeresRepository puntoRepo;

    @Autowired
    public puntointeresService(puntointeresRepository puntoRepo) {
        this.puntoRepo = puntoRepo;
    }
    /// guardar + nombre
    public puntointeresEntity guardarPunto(puntointeresEntity punto) {
        return puntoRepo.save(punto);
    }

    /// obtener todos los + nombre
    public List<puntointeresEntity> obtenerAllPunto() {
        return puntoRepo.findAll();
    }

    /// obtener por id
    public Optional<puntointeresEntity> obtenerPuntoPorId(Long id) {
        return puntoRepo.findById(id);
    }

    /// actualizar + nombre
    public  puntointeresEntity actualizarPunto(puntointeresEntity punto) {
        return puntoRepo.save(punto);
    }

    ///  eliminar
    public void eliminarPunto(Long id) {
        puntoRepo.deleteById(id);
    }
    //si se agregoi algo al repository
    public List<puntointeresEntity> obtenerPuntoPorNombre(String nombre) {
        return puntoRepo.findByNombreInteres(nombre);
    }
}
