package com.yagosiqueira.medical_appointment_api.exception;

public class MedicoNaoEncontradoException extends RuntimeException {
    public MedicoNaoEncontradoException(Long id) {
        super("Médico não encontrado com o id: " + id);
    }
}
