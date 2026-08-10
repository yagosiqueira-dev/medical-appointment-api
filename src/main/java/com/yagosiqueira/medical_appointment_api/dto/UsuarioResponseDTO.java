package com.yagosiqueira.medical_appointment_api.dto;

import com.yagosiqueira.medical_appointment_api.enums.Role;

public record UsuarioResponseDTO(
        Long id,
        String email,
        Role role
) {}
