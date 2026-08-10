package com.yagosiqueira.medical_appointment_api.dto;

import com.yagosiqueira.medical_appointment_api.enums.StatusConsulta;

import java.time.LocalDateTime;

public record ConsultaResponseDTO(
        Long id,
        String nomeMedico,
        String nomePaciente,
        LocalDateTime dataHora,
        StatusConsulta status
) {}
