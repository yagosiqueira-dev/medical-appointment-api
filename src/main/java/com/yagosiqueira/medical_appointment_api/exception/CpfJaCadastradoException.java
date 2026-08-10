package com.yagosiqueira.medical_appointment_api.exception;

public class CpfJaCadastradoException extends RuntimeException {
    public CpfJaCadastradoException(String cpf) {
        super("Já existe um paciente cadastrado com o CPF: " + cpf);
    }
}
