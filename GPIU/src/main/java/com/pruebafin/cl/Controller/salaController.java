package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.salaEntity;
import com.pruebafin.cl.Service.salaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
@CrossOrigin(origins = "*")
public class salaController {
    private salaService salaSrvc;

    @Autowired
    public salaController(salaService salaSrvc) {
        this.salaSrvc = salaSrvc;
    }

    @GetMapping
    public ResponseEntity<List<salaEntity>> obtenerTodasLasSalas(){
        List<salaEntity> salas = salaSrvc.obtenerAllSalas();
        return new ResponseEntity<>(salas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<salaEntity> obtenerSalaPorId(@PathVariable Long id){
        return salaSrvc.obtenerSalaPorId(id)
                .map(sala -> new ResponseEntity<>(sala, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<salaEntity> crearSala(@RequestBody salaEntity sala){
        salaEntity nuevaSala = salaSrvc.guardarSala(sala);
        return new ResponseEntity<>(nuevaSala, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<salaEntity> actualizarSala(@PathVariable Long id, @RequestBody salaEntity sala){
        return salaSrvc.obtenerSalaPorId(id)
                .map(salaExiste -> {
                    sala.setId_sala(id);
                    return new ResponseEntity<>(salaSrvc.actualizarSala(sala), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSalaPorId(@PathVariable Long id){
        return salaSrvc.obtenerSalaPorId(id)
                .map(sala -> {
                    salaSrvc.eliminarSalaPorId(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
