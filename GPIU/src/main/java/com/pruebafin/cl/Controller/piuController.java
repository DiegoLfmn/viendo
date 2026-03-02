package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.piuEntity;
import com.pruebafin.cl.Service.piuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pius")
@CrossOrigin(origins = "*")
public class piuController {
    private piuService piuSrvc;

    @Autowired
    public piuController(piuService piuSrvc) {
        this.piuSrvc = piuSrvc;
    }

    @GetMapping
    public ResponseEntity<List<piuEntity>> obtenerTodosLosPius() {
        List<piuEntity> pius = piuSrvc.obtenerAllPiu();
        return new ResponseEntity<>(pius, HttpStatus.OK);
    }

    @GetMapping("/api/pius/{id}")
    public ResponseEntity<piuEntity> obtenerIdDelPiu(@PathVariable Long id){
        return piuSrvc.obtenerPiuById(id)
                .map(piuId -> new ResponseEntity<>(piuId,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<piuEntity> crearPiu(@RequestBody piuEntity piu){
        piuEntity nuevoPiu = piuSrvc.guardarPiu(piu);
        return new ResponseEntity<>(nuevoPiu, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<piuEntity> actualizarPiu(@PathVariable Long id, @RequestBody piuEntity piu){
        return piuSrvc.obtenerPiuById(id)
                .map(piuExiste ->{
                    piu.setId_piu(id);
                    return new ResponseEntity<>(piuSrvc.actualizarPiu(piu),  HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarPiuPorId(@PathVariable Long id){
        return piuSrvc.obtenerPiuById(id)
                .map(piu ->{
                    piuSrvc.eliminarPiu(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



}
