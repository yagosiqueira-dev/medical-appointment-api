package com.yagosiqueira.medical_appointment_api.dto;

import java.time.LocalDate;

public record PacienteResponseDTO(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento
) {}
