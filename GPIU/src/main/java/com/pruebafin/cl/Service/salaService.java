package com.pruebafin.cl.Service;

import com.pruebafin.cl.Entity.salaEntity;
import com.pruebafin.cl.Repository.salaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class salaService {
    private salaRepository salaRepo;

    @Autowired
    public salaService(salaRepository salaRepo) {
        this.salaRepo = salaRepo;
    }
    /// guardar + nombre
    public salaEntity guardarSala(salaEntity sala){
        return salaRepo.save(sala);
    }

    /// obtener todos los + nombre
    public List<salaEntity> obtenerAllSalas(){
        return salaRepo.findAll();
    }

    /// obtener por id
    public Optional<salaEntity>  obtenerSalaPorId(Long id){
        return salaRepo.findById(id);
    }

    /// actualizar + nombre
    public salaEntity actualizarSala(salaEntity sala){
        return salaRepo.save(sala);
    }

    ///  eliminar
    public void eliminarSalaPorId(Long id){
        salaRepo.deleteById(id);
    }
    //si se agregoi algo al repository
//    public List<salaEntity> encontrarPorCodigo(String codigo){
//        return  salaRepo.findByCodigo(codigo);
//    }
//    public List<salaEntity> encontrarPorPiso(int piso){
//        return  salaRepo.findByPiso(piso);
//    }
//    public List<salaEntity> encontrarPorCapacidad(String nombre){
//        return  salaRepo.findByCapacidad(nombre);
//    }
}
