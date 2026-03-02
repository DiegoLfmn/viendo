package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.rutaEntity;
import com.pruebafin.cl.Repository.rutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class rutaService {
    private rutaRepository rutaRepo;

    @Autowired
    public rutaService(rutaRepository rutaRepo) {
        this.rutaRepo = rutaRepo;
    }
    /// guardar + nombre
    public rutaEntity guardarRuta(rutaEntity ruta){
        return rutaRepo.save(ruta);
    }

    /// obtener todos los + nombre
    public List<rutaEntity> obtenerAllRutas(){
        return rutaRepo.findAll();
    }

    /// obtener por id
    public Optional<rutaEntity> obtenerRutaById(Long id){
        return rutaRepo.findById(id);
    }

    /// actualizar + nombre
    public rutaEntity actualizarRuta(rutaEntity ruta){
        return rutaRepo.save(ruta);
    }

    ///  eliminar
    public void eliminarRuta(Long id){
        rutaRepo.deleteById(id);
    }
    //si se agregoi algo al repository
    //public
}
