package com.vidaanimal.vidaanimal_backend.service;

import com.vidaanimal.vidaanimal_backend.entity.Cliente;
import com.vidaanimal.vidaanimal_backend.exception.BusinessException;
import com.vidaanimal.vidaanimal_backend.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorUsuarioId(Integer usuarioId) {
        return clienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new BusinessException("Cliente no encontrado para ese usuario"));
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cliente no encontrado"));
    }
}