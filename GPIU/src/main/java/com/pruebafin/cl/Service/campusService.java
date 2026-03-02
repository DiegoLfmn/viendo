package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.campusEntity;
import com.pruebafin.cl.Repository.campusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class campusService {
    private campusRepository campusRepo;


    @Autowired
    public campusService(campusRepository campusRepo) {
        this.campusRepo = campusRepo;
    }

    /// guardar + nombre
    public campusEntity guardarCampus(campusEntity campus){
        return campusRepo.save(campus);
    }

    /// obtener todos los + nombre
    public List<campusEntity> obtenerAllCampus(){
        return campusRepo.findAll();
    }

    /// obtener por id
    public Optional<campusEntity> obtenerCampusPorId(Long id){
        return campusRepo.findById(id);
    }

    /// actualizar + nombre
    public campusEntity actualizarCampus(campusEntity campus){
        return campusRepo.save(campus);
    }

    ///  eliminar
    public void eliminarCampusPorId(Long id){
        campusRepo.deleteById(id);
    }
    //si se agregoi algo al repository
//    public campusEntity obtenerCampusPorNombre(String nombre){
//        return campusRepo.findByName(nombre).get(0);
//    }
}
