package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.edificioEntity;
import com.pruebafin.cl.Service.edificioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/edificios")
@CrossOrigin(origins = "*")
public class edificioController {
    private edificioService edificioSrvc;

    @Autowired
    public edificioController(edificioService edificioSrvc) {
        this.edificioSrvc = edificioSrvc;
    }

    @GetMapping
    public ResponseEntity<List<edificioEntity>> obtenerTodosLosEdificios(){
        List<edificioEntity> edificios = edificioSrvc.obtenerAllEdificios();
        return new ResponseEntity<>(edificios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<edificioEntity> obtenerEdificioPorId(@PathVariable Long id){
        return edificioSrvc.obtenerEdificioPorId(id)
                .map(edificio -> new ResponseEntity<>(edificio, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<edificioEntity> crearEdificio(@RequestBody edificioEntity edificio){
        edificioEntity nuevoEdificio = edificioSrvc.guardarEdificio(edificio);
        return new ResponseEntity<>(nuevoEdificio, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<edificioEntity> actualizarEdificio(@PathVariable Long id, @RequestBody edificioEntity edificio){
        return edificioSrvc.obtenerEdificioPorId(id)
                .map(edificioExiste -> {
                    edificio.setId_edificio(id);
                    return new ResponseEntity<>(edificioSrvc.actualizarEdificio(edificio), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEdificioPorId(@PathVariable Long id){
        return edificioSrvc.obtenerEdificioPorId(id)
                .map(edificio -> {
                    edificioSrvc.eliminarEdificioPorId(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
