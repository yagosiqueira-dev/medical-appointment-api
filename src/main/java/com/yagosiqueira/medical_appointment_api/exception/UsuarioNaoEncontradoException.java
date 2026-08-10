package com.yagosiqueira.medical_appointment_api.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(Long id) {
        super("Usuário não encontrado com o id: " + id);
    }
}
