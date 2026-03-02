package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.eventoEntity;
import com.pruebafin.cl.Entity.usuarioEntity;
import com.pruebafin.cl.Repository.eventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class eventoService {
    private eventoRepository eventoRepo;

    @Autowired
    public eventoService(eventoRepository eventoRepo) {
        this.eventoRepo = eventoRepo;
    }

    public eventoEntity guardarEvento(eventoEntity evento) {
        return eventoRepo.save(evento);
    }

    public List<eventoEntity> obtenerAllEvento() {
        return eventoRepo.findAll();
    }

    public Optional<eventoEntity> obtenerEventoPorId(Long id) {
        return eventoRepo.findById(id);
    }

    public eventoEntity actualizarEvento(eventoEntity evento) {
        return eventoRepo.save(evento);
    }

    public void eliminarEvento(Long id) {
        eventoRepo.deleteById(id);
    }

}
