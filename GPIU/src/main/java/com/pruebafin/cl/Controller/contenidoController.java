package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.contenidoEntity;
import com.pruebafin.cl.Service.contenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .map(contenidoId -> new ResponseEntity<>(contenidoId,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

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
        contenidoEntity nuevoContenido = contService.guardarContenido(contenido);
        return new ResponseEntity<>(nuevoContenido, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<contenidoEntity> actualizarContenido(@PathVariable Long id, @RequestBody contenidoEntity contenido){
        return contService.obtenerContenidoById(id)
                .map(contenidoExiste -> {
                    contenido.setId_contenido(id);
                    return new ResponseEntity<>(contService.actualizarContenido(contenido),HttpStatus.OK);
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
    public ResponseEntity<Void>  eliminarContenido(@PathVariable Long id){
        return contService.obtenerContenidoById(id)
                .map(contenido -> {
                    contService.eliminarContenido(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



}
