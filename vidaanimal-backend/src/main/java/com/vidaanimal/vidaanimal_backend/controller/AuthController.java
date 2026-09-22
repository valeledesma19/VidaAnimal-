package com.vidaanimal.vidaanimal_backend.controller;

import com.vidaanimal.vidaanimal_backend.dto.LoginRequest;
import com.vidaanimal.vidaanimal_backend.dto.LoginResponse;
import com.vidaanimal.vidaanimal_backend.dto.RegistroRequest;
import com.vidaanimal.vidaanimal_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/registro")
    public ResponseEntity<LoginResponse> registro(@Valid @RequestBody RegistroRequest request) {
        return ResponseEntity.ok(authService.registro(request));
    }
}