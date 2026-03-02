package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.solicituddesconexionpiuEntity;
import com.pruebafin.cl.Repository.solicituddesconexionpiuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class solicituddesconexionpiuService {
    private solicituddesconexionpiuRepository soliRepo;


    @Autowired
    public solicituddesconexionpiuService(solicituddesconexionpiuRepository soliRepo) {
        this.soliRepo = soliRepo;
    }

    /// guardar + nombre
    public solicituddesconexionpiuEntity guardarSolicitud(solicituddesconexionpiuEntity solicitud) {
        return soliRepo.save(solicitud);
    }

    /// obtener todos los + nombre
    public List<solicituddesconexionpiuEntity> obtenerAllSoli() {
        return soliRepo.findAll();
    }

    /// obtener por id
    public Optional<solicituddesconexionpiuEntity> obtenerSoliById(Long id) {
        return soliRepo.findById(id);
    }

    /// actualizar + nombre
    public solicituddesconexionpiuEntity actualizarSoli(solicituddesconexionpiuEntity solicitud) {
        return soliRepo.save(solicitud);
    }

    ///  eliminar
    public void eliminarSoli(Long id) {
        soliRepo.deleteById(id);
    }
    //si se agregoi algo al repository
    //public
}
