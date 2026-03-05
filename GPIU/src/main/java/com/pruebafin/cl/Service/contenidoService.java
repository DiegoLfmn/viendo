package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.contenidoEntity;

import com.pruebafin.cl.Repository.contenidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class contenidoService {
    private contenidoRepository contenidoRepo;

    @Autowired
    public contenidoService (contenidoRepository contenidoRepo) {
        this.contenidoRepo = contenidoRepo;
    }

    public contenidoEntity guardarContenido(contenidoEntity contenido){
        return contenidoRepo.save(contenido);
    }

    public List<contenidoEntity> obtenerAllContenidos(){
        return contenidoRepo.findAll();
    }

    public Optional<contenidoEntity> obtenerContenidoById(Long id){
        return contenidoRepo.findById(id);
    }

    public contenidoEntity actualizarContenido(contenidoEntity contenido){
        return contenidoRepo.save(contenido);
    }

    public void eliminarContenido(Long id){
        contenidoRepo.deleteById(id);
    }

    public List<contenidoEntity> obtenerPorTipo(String tipo){
        return contenidoRepo.findByTipo(tipo);
    }
<<<<<<< HEAD

    public List<contenidoEntity> obtenerPorTitulo(String titulo){
        return contenidoRepo.findByTitulo(titulo);
    }
=======
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e

    public List<contenidoEntity> obtenerPorTitulo(String titulo){
        return contenidoRepo.findByTitulo(titulo);
    }

    public List<contenidoEntity> obtenerPorEstado(String estado){
        return contenidoRepo.findByEstado(estado);
    }

}