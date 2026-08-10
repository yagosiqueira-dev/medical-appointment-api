package com.yagosiqueira.medical_appointment_api.exception;

public class HorarioIndisponivelException extends RuntimeException {
    public HorarioIndisponivelException() {
        super("O médico já possui uma consulta agendada nesse horário.");
    }
}
