package com.yagosiqueira.medical_appointment_api.exception;

public class PacienteNaoEncontradoException extends RuntimeException {
    public PacienteNaoEncontradoException(Long id) {
        super("Paciente não encontrado com o id: " + id);
    }
}
