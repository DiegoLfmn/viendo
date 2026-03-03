package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.piuEntity;
import com.pruebafin.cl.Repository.piuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class piuService {
    private piuRepository piuRepo;

    @Autowired
    public piuService(piuRepository piuRepo) {
        this.piuRepo = piuRepo;
    }
    /// guardar + nombre
    public piuEntity guardarPiu(piuEntity piu) {
        return piuRepo.save(piu);
    }

    /// obtener todos los + nombre
    public List<piuEntity> obtenerAllPiu() {
        return piuRepo.findAll();
    }

    /// obtener por id
    public Optional<piuEntity> obtenerPiuById(Long id) {
        return piuRepo.findById(id);
    }

    /// actualizar + nombre
    public piuEntity actualizarPiu(piuEntity piu) {
        return piuRepo.save(piu);
    }

    ///  eliminar
    public void  eliminarPiu(Long id) {
        piuRepo.deleteById(id);
    }

    public List<piuEntity> obtenerPIUsPorEstado(String estado) {
        return piuRepo.findByEstadoPiu(estado);
    }

//    //si se agregoi algo al repository
    public List<piuEntity> encontrarByFechaInstalacion(Date fecha) {
        return piuRepo.findByfechaInstalacion(fecha);
    }
}
