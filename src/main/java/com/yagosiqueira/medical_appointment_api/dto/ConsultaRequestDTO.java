package com.yagosiqueira.medical_appointment_api.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaRequestDTO(

        @NotNull(message = "O médico é obrigatório")
        Long medicoId,

        @NotNull(message = "O paciente é obrigatório")
        Long pacienteId,

        @NotNull(message = "A data e hora são obrigatórias")
        @Future(message = "A data da consulta deve estar no futuro")
        LocalDateTime dataHora
) {}
