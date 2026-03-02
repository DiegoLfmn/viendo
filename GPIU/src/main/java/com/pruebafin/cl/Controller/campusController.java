package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.campusEntity;
import com.pruebafin.cl.Service.campusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campus")
@CrossOrigin(origins = "*")
public class campusController {
    private campusService campusSrvc;

    @Autowired
    public campusController(campusService campusSrvc) {
        this.campusSrvc = campusSrvc;
    }

    @GetMapping
    public ResponseEntity<List<campusEntity>> obtenerTodosLosCampus(){
        List<campusEntity> campus = campusSrvc.obtenerAllCampus();
        return new ResponseEntity<>(campus, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<campusEntity> obtenerCampusPorId(@PathVariable Long id){
        return campusSrvc.obtenerCampusPorId(id)
                .map(campus -> new ResponseEntity<>(campus, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<campusEntity> crearCampus(@RequestBody campusEntity campus){
        campusEntity nuevoCampus = campusSrvc.guardarCampus(campus);
        return new ResponseEntity<>(nuevoCampus, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<campusEntity> actualizarCampus(@PathVariable Long id, @RequestBody campusEntity campus){
        return campusSrvc.obtenerCampusPorId(id)
                .map(campusExiste -> {
                    campus.setId_campus(id);
                    return new ResponseEntity<>(campusSrvc.actualizarCampus(campus), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCampusPorId(@PathVariable Long id){
        return campusSrvc.obtenerCampusPorId(id)
                .map(campus -> {
                    campusSrvc.eliminarCampusPorId(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
