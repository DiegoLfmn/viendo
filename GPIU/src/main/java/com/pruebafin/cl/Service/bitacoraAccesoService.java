package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.bitacoraAccesoEntity;
import com.pruebafin.cl.Entity.piuEntity;
import com.pruebafin.cl.Entity.usuarioEntity;
import com.pruebafin.cl.Repository.bitacoraAccesoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class bitacoraAccesoService {
    private bitacoraAccesoRepository bitaRepo;

    @Autowired
    public bitacoraAccesoService(bitacoraAccesoRepository bitaRepo) {
        this.bitaRepo = bitaRepo;
    }

    public bitacoraAccesoEntity guardarBitacora(bitacoraAccesoEntity bitacora ){
        return bitaRepo.save(bitacora);
    }

    public Optional<bitacoraAccesoEntity> obtenerBitaPorId(Long id){
        return bitaRepo.findById(id);
    }

    public bitacoraAccesoEntity actualizarBita(bitacoraAccesoEntity bitacora ){
        return bitaRepo.save(bitacora);
    }


    public void eliminarBita(Long id){
        bitaRepo.deleteById(id);
    }

    public List<bitacoraAccesoEntity> obtenerByPIU(piuEntity piu){
        return bitaRepo.findBypiuDeBita(piu);
    }



    public List<bitacoraAccesoEntity> findByUsuario(usuarioEntity usuario){
        return bitaRepo.findByusuario(usuario);
    }

}
