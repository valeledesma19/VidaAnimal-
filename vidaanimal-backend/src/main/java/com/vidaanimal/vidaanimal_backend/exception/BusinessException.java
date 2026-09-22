package com.vidaanimal.vidaanimal_backend.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String mensaje) {
        super(mensaje);
    }
}