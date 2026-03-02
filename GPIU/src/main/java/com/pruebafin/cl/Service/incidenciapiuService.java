package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.incidenciapiuEntity;
import com.pruebafin.cl.Repository.incidenciapiuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class incidenciapiuService {
    private incidenciapiuRepository incidenciaRepo;

    @Autowired
    public incidenciapiuService(incidenciapiuRepository incidenciaRepo) {
        this.incidenciaRepo = incidenciaRepo;
    }
    /// guardar + nombre
    public incidenciapiuEntity guardarIncidencia(incidenciapiuEntity incidencia) {
        return incidenciaRepo.save(incidencia);

    }

    /// obtener todos los + nombre
    public List<incidenciapiuEntity> obtenerAllIncidencia() {
        return  incidenciaRepo.findAll();
    }

    /// obtener por id
    public Optional<incidenciapiuEntity> obtenerIncidenciaById(Long id) {
        return incidenciaRepo.findById(id);
    }

    /// actualizar + nombre
    public incidenciapiuEntity actualizarIncidencia(incidenciapiuEntity incidencia) {
        return incidenciaRepo.save(incidencia);
    }

    ///  eliminar
    public void eliminarIncidencia(Long id) {
        incidenciaRepo.deleteById(id);
    }
    //si se agregoi algo al repository
    //public
}
