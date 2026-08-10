package com.yagosiqueira.medical_appointment_api.dto;

import com.yagosiqueira.medical_appointment_api.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String senha,

        @NotNull(message = "O perfil (role) é obrigatório")
        Role role
) {}