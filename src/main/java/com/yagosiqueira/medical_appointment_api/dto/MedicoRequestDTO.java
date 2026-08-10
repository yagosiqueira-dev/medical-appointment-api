package com.yagosiqueira.medical_appointment_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MedicoRequestDTO(

        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O CRM é obrigatório")
        String crm,

        @NotNull(message = "O usuário vinculado é obrigatório")
        Long usuarioId,

        List<Long> especialidadeIds
) {}
