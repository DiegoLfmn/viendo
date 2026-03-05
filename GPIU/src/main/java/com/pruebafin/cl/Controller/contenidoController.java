package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.contenidoEntity;
import com.pruebafin.cl.Service.contenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contenidos")
@CrossOrigin(origins = "*")
public class contenidoController {
    private contenidoService contService;

    @Autowired
    public contenidoController(contenidoService contService) {
        this.contService = contService;
    }

    @GetMapping
    public ResponseEntity<List<contenidoEntity>> obtenerTodosLosContenidos(){
        List<contenidoEntity> contenidos = contService.obtenerAllContenidos();
        return new ResponseEntity<>(contenidos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<contenidoEntity> obtenerPorId(@PathVariable Long id){
        return contService.obtenerContenidoById(id)
                .map(contenidoId -> new ResponseEntity<>(contenidoId, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

<<<<<<< HEAD
=======
    @GetMapping("/publicados")
    public ResponseEntity<List<contenidoEntity>> obtenerPublicados(){
        List<contenidoEntity> publicados = contService.obtenerPorEstado("PUBLICADO");
        return new ResponseEntity<>(publicados, HttpStatus.OK);
    }

>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
    @GetMapping("/procesos")
    public ResponseEntity<List<contenidoEntity>> obtenerProcesos(){
        List<contenidoEntity> procesos = contService.obtenerPorTipo("PROCESO");
        return new ResponseEntity<>(procesos, HttpStatus.OK);
    }

    @GetMapping("/info-general")
    public ResponseEntity<List<contenidoEntity>> obtenerInfoGeneral(){
        List<contenidoEntity> infoGeneral = contService.obtenerPorTipo("INFO_GENERAL");
        return new ResponseEntity<>(infoGeneral, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<contenidoEntity> crearContenido(@RequestBody contenidoEntity contenido){
        // Siempre forzar estado inicial BORRADOR y fecha de creación
        contenido.setEstado("BORRADOR");
        contenido.setFecha_creacion(new Date());
        // Si el frontend envía el nombre del autor como autorNombre, lo preservamos
        contenidoEntity nuevoContenido = contService.guardarContenido(contenido);
        return new ResponseEntity<>(nuevoContenido, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<contenidoEntity> actualizarContenido(@PathVariable Long id, @RequestBody contenidoEntity contenido){
        return contService.obtenerContenidoById(id)
                .map(contenidoExiste -> {
                    contenido.setId_contenido(id);
                    contenido.setFecha_modificacion(new Date());
                    return new ResponseEntity<>(contService.actualizarContenido(contenido), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}/validar")
    public ResponseEntity<contenidoEntity> enviarAValidacion(@PathVariable Long id){
        return contService.obtenerContenidoById(id)
                .map(contenido -> {
                    contenido.setEstado("EN_VALIDACION");
                    return new ResponseEntity<>(contService.actualizarContenido(contenido), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<contenidoEntity> aprobarContenido(@PathVariable Long id){
        return contService.obtenerContenidoById(id)
                .map(contenido -> {
                    contenido.setEstado("PUBLICADO");
<<<<<<< HEAD
=======
                    contenido.setFecha_publicacion(new Date());
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
                    return new ResponseEntity<>(contService.actualizarContenido(contenido), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<contenidoEntity> rechazarContenido(@PathVariable Long id, @RequestBody(required = false) Map<String, String> body){
        return contService.obtenerContenidoById(id)
                .map(contenido -> {
                    contenido.setEstado("RECHAZADO");
                    return new ResponseEntity<>(contService.actualizarContenido(contenido), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<Void>  eliminarContenido(@PathVariable Long id){
=======
    public ResponseEntity<Void> eliminarContenido(@PathVariable Long id){
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
        return contService.obtenerContenidoById(id)
                .map(contenido -> {
                    contService.eliminarContenido(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}