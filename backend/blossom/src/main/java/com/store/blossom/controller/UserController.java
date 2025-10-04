package com.store.blossom.controller;

import com.store.blossom.config.JwtUtil;
import com.store.blossom.dto.UserLoginDto;
import com.store.blossom.dto.UserRegistrationDto;
import com.store.blossom.model.User;
import com.store.blossom.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // Registro
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationDto dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto) {
        // Buscar el usuario
        User user = userService.findByUsername(dto.getUsername());
        if (user == null) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        // Validar contraseña
        boolean success = userService.login(dto);
        if (!success) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // Generar token
        String token = jwtUtil.generateToken(dto.getUsername());

        // Retornar token + id del usuario
        return ResponseEntity.ok(java.util.Map.of(
            "token", token,
            "userId", user.getId()
        ));
    }


}
