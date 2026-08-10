package com.yagosiqueira.medical_appointment_api.dto;

import jakarta.validation.constraints.NotBlank;

public record EspecialidadeRequestDTO(

        @NotBlank(message = "O nome da especialidade é obrigatório")
        String nome
) {}
