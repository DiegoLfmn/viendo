package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.usuarioEntity;
import com.pruebafin.cl.Service.usuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")

public class usuarioController {
    private usuarioService usuService;

    @Autowired
    public usuarioController(usuarioService usuService) {
        this.usuService = usuService;
    }
    @GetMapping//GET -> /api/usuarios
    public ResponseEntity<List<usuarioEntity>> obtenerTodosLosUsuarios(){
        List<usuarioEntity> usuarios = usuService.obtenerAllUsers();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    @GetMapping("/api/usuario/{id}")
    public ResponseEntity<usuarioEntity> obtenerPorId(@PathVariable Long id){
        return usuService.obtenerUsuarioPorId(id)
                .map(usuarioId -> new ResponseEntity<>(usuarioId,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<usuarioEntity> crearUsuario(@RequestBody usuarioEntity usuario){
        usuarioEntity nuevoUsuario = usuService.guardarUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario,HttpStatus.OK);
    }

    @PutMapping("/{id}") //PUT -> /api/usuarios/3
    public ResponseEntity<usuarioEntity> actualizarUsuario(@PathVariable Long id, @RequestBody usuarioEntity usuario){
        return usuService.obtenerUsuarioPorId(id)
                .map(usuarioExiste -> {
                    usuario.setId_usuario(id);
                    return new ResponseEntity<>(usuService.actualizarUsuario(usuario),HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarElUsuarioId(@PathVariable Long id){
        return usuService.obtenerUsuarioPorId(id)
                .map(usuario -> {
                    usuService.eliminarUsuario(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
