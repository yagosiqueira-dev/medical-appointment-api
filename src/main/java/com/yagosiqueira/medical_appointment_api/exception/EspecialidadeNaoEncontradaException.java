package com.yagosiqueira.medical_appointment_api.exception;

public class EspecialidadeNaoEncontradaException extends RuntimeException {
    public EspecialidadeNaoEncontradaException(Long id) {
        super("Especialidade não encontrada com o id: " + id);
    }
}
