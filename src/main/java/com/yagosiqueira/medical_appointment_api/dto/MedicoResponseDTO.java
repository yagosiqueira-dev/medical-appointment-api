package com.yagosiqueira.medical_appointment_api.dto;

import java.util.List;

public record MedicoResponseDTO(
        Long id,
        String nome,
        String crm,
        List<String> especialidades
) {}
