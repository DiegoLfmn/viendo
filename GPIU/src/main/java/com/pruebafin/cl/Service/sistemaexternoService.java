package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.sistemaexternoEntity;
import com.pruebafin.cl.Repository.sistemaexternoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class sistemaexternoService {
    private sistemaexternoRepository sistemaRepo;

    @Autowired
    public sistemaexternoService(sistemaexternoRepository sistemaRepo) {
        this.sistemaRepo = sistemaRepo;
    }

    public sistemaexternoEntity guardarSistema(sistemaexternoEntity sistema) {
        return sistemaRepo.save(sistema);
    }

    public List<sistemaexternoEntity> findAllSistema() {
        return sistemaRepo.findAll();
    }

    public Optional<sistemaexternoEntity> obtenerSistemaPorId(Long id) {
        return sistemaRepo.findById(id);
    }

    public sistemaexternoEntity actualizarSistema(sistemaexternoEntity sistema) {
        return sistemaRepo.save(sistema);
    }

    public void eliminarSistemaExterno(Long id) {
        sistemaRepo.deleteById(id);
    }

    public List<sistemaexternoEntity> obtenerPorNombreSistema(String nombre) {
        return sistemaRepo.findByNombreSistext(nombre);
    }


}
