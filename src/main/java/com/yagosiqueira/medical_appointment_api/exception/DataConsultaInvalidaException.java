package com.yagosiqueira.medical_appointment_api.exception;

public class DataConsultaInvalidaException extends RuntimeException {
    public DataConsultaInvalidaException() {
        super("Não é possível agendar uma consulta em uma data/horário no passado.");
    }
}
