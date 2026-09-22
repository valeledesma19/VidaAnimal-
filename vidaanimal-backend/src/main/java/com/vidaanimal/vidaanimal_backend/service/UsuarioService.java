package com.vidaanimal.vidaanimal_backend.service;

import com.vidaanimal.vidaanimal_backend.entity.Usuario;
import com.vidaanimal.vidaanimal_backend.exception.BusinessException;
import com.vidaanimal.vidaanimal_backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));
    }

    public boolean existeEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public Usuario guardarConPasswordHasheada(Usuario usuario, String passwordPlano) {
        if (existeEmail(usuario.getEmail())) {
            throw new BusinessException("Ya existe una cuenta con ese email");
        }
        usuario.setContraseñaHash(passwordEncoder.encode(passwordPlano));
        return usuarioRepository.save(usuario);
    }
}