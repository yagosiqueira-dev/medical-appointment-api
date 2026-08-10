package com.yagosiqueira.medical_appointment_api.exception;

public class ConsultaNaoEncontradaException extends RuntimeException {
    public ConsultaNaoEncontradaException(Long id) {
        super("Consulta não encontrada com o id: " + id);
    }
}