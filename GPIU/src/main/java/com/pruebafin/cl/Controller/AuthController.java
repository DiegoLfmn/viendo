package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.usuarioEntity;
import com.pruebafin.cl.Repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private usuarioRepository usuarioRepo;

    // In-memory token store (prototype only — use JWT in production)
    private final ConcurrentHashMap<String, Long> tokenStore = new ConcurrentHashMap<>();

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credenciales) {
        String correo = credenciales.get("correo");
        String contrasena = credenciales.get("contrasena");

        if (correo == null || contrasena == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Correo y contraseña son requeridos");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Optional<usuarioEntity> usuarioOpt = usuarioRepo.findByCorreoUsuario(correo);

        if (usuarioOpt.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Credenciales inválidas");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        usuarioEntity usuario = usuarioOpt.get();

        // Prototype: plain text comparison — use BCrypt in production
        String storedPassword = usuario.getContrasena_usuario() != null
                ? usuario.getContrasena_usuario()
                : usuario.getPassword_usuario();
        if (storedPassword == null || !contrasena.equals(storedPassword)) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Credenciales inválidas");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        String estado = usuario.getEstado_cuenta();
        if ("bloqueado".equalsIgnoreCase(estado) || "inactivo".equalsIgnoreCase(estado)) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Cuenta bloqueada o inactiva");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        // Generate token and store it
        String token = java.util.UUID.randomUUID().toString();
        tokenStore.put(token, usuario.getId_usuario());

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("token", token);
        respuesta.put("user", Map.of(
            "id", usuario.getId_usuario(),
            "nombre", usuario.getNombre_usuario(),
            "correo", usuario.getCorreo_usuario(),
            "roles", usuario.getRoles().stream()
                .map(r -> r.getNombre_rol().toUpperCase())
                .toList()
        ));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            tokenStore.remove(authHeader.substring(7));
        }
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada"));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(Map.of("error", "No autenticado"), HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7);
        Long userId = tokenStore.get(token);
        if (userId == null) {
            return new ResponseEntity<>(Map.of("error", "Token inválido"), HttpStatus.UNAUTHORIZED);
        }
        Optional<usuarioEntity> usuarioOpt = usuarioRepo.findById(userId);
        if (usuarioOpt.isEmpty()) {
            return new ResponseEntity<>(Map.of("error", "Usuario no encontrado"), HttpStatus.UNAUTHORIZED);
        }
        usuarioEntity usuario = usuarioOpt.get();
        Map<String, Object> user = new HashMap<>();
        user.put("id", usuario.getId_usuario());
        user.put("nombre", usuario.getNombre_usuario());
        user.put("correo", usuario.getCorreo_usuario());
        user.put("roles", usuario.getRoles().stream()
                .map(r -> r.getNombre_rol().toUpperCase())
                .toList());
        return ResponseEntity.ok(user);
    }
}
