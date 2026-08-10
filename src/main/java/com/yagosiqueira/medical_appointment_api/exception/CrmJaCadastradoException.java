package com.yagosiqueira.medical_appointment_api.exception;

public class CrmJaCadastradoException extends RuntimeException {
    public CrmJaCadastradoException(String crm) {
        super("Já existe um médico cadastrado com o CRM: " + crm);
    }
}
