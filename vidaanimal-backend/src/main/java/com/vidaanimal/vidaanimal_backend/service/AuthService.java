package com.vidaanimal.vidaanimal_backend.service;

import com.vidaanimal.vidaanimal_backend.dto.LoginRequest;
import com.vidaanimal.vidaanimal_backend.dto.LoginResponse;
import com.vidaanimal.vidaanimal_backend.dto.RegistroRequest;
import com.vidaanimal.vidaanimal_backend.entity.Cliente;
import com.vidaanimal.vidaanimal_backend.entity.Rol;
import com.vidaanimal.vidaanimal_backend.entity.Usuario;
import com.vidaanimal.vidaanimal_backend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioService usuarioService;
    private final ClienteService clienteService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioService usuarioService, ClienteService clienteService,
                       JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioService.buscarPorEmail(request.getEmail());
        String token = jwtService.generarToken(usuario.getEmail(), usuario.getRol().name());
        return new LoginResponse(token, usuario.getRol().name());
    }

    public LoginResponse registro(RegistroRequest request) {
        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setRol(Rol.CLIENTE);
        usuario = usuarioService.guardarConPasswordHasheada(usuario, request.getPassword());

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setTelefono(request.getTelefono());
        clienteService.guardar(cliente);

        String token = jwtService.generarToken(usuario.getEmail(), usuario.getRol().name());
        return new LoginResponse(token, usuario.getRol().name());
    }
}