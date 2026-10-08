package com.vidaanimal.vidaanimal_backend.controller;

import com.vidaanimal.vidaanimal_backend.dto.LoginResponse;
import com.vidaanimal.vidaanimal_backend.exception.BusinessException;
import com.vidaanimal.vidaanimal_backend.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Test
    void login_credencialesValidas_devuelve200ConToken() throws Exception {
        when(authService.login(any())).thenReturn(new LoginResponse("token-falso", "CLIENTE"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("""
                                {"email":"cliente1@test.com","password":"cliente1234"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-falso"))
                .andExpect(jsonPath("$.rol").value("CLIENTE"));
    }

    @Test
    void login_credencialesInvalidas_devuelve401() throws Exception {
        when(authService.login(any())).thenThrow(new BadCredentialsException("mal"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("""
                                {"email":"cliente1@test.com","password":"incorrecta"}
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Email o contraseña incorrectos"));
    }

    @Test
    void login_sinEmail_devuelve400PorValidacion() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("""
                                {"password":"cliente1234"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registro_emailDuplicado_devuelve400() throws Exception {
        when(authService.registro(any())).thenThrow(new BusinessException("Ya existe una cuenta con ese email"));

        mockMvc.perform(post("/api/auth/registro")
                        .contentType("application/json")
                        .content("""
                                {"email":"existente@test.com","password":"12345678","nombre":"Ana","apellido":"Lopez","telefono":"123"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Ya existe una cuenta con ese email"));
    }
}