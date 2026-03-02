package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.eventoEntity;
import com.pruebafin.cl.Service.eventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*")
public class eventoController {
    private eventoService eveService;

    @Autowired
    public eventoController(eventoService eveService) {
        this.eveService = eveService;
    }
    @GetMapping
    public ResponseEntity<List<eventoEntity>> obtenerTodosLosEventos(){
        List<eventoEntity> eventos = eveService.obtenerAllEvento();
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }

    @GetMapping("/api/eventos/{id}")
    public ResponseEntity<eventoEntity> obtenerIdDeEvento(@PathVariable Long id){
        return eveService.obtenerEventoPorId(id)
                .map(eventoId -> new ResponseEntity<>(eventoId,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<eventoEntity> crearEvento(@RequestBody eventoEntity evento){
        eventoEntity nuevoEvento = eveService.guardarEvento(evento);
        return new ResponseEntity<>(nuevoEvento,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<eventoEntity> actualizarEvento(@PathVariable Long id, @RequestBody eventoEntity evento){
        return eveService.obtenerEventoPorId(id)
                .map(eventoExiste -> {
                    evento.setId_contenido(id);
                    return  new ResponseEntity<>(eveService.actualizarEvento(evento),HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarEventoPorId(@PathVariable Long id){
        return eveService.obtenerEventoPorId(id)
                .map(evento ->{
                    eveService.eliminarEvento(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
