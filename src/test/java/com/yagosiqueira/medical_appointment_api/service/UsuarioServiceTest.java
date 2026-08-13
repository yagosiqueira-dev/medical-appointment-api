package com.yagosiqueira.medical_appointment_api.service;

import com.yagosiqueira.medical_appointment_api.dto.UsuarioRequestDTO;
import com.yagosiqueira.medical_appointment_api.dto.UsuarioResponseDTO;
import com.yagosiqueira.medical_appointment_api.entity.Usuario;
import com.yagosiqueira.medical_appointment_api.enums.Role;
import com.yagosiqueira.medical_appointment_api.exception.EmailJaCadastradoException;
import com.yagosiqueira.medical_appointment_api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveCriarUsuarioComSucesso() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("paciente@teste.com", "123456", Role.PACIENTE);

        Usuario usuarioSalvo = Usuario.builder()
                .id(1L)
                .email(dto.email())
                .senha("senha-criptografada")
                .role(Role.PACIENTE)
                .build();

        when(usuarioRepository.existsByEmail(dto.email())).thenReturn(false);
        when(passwordEncoder.encode(dto.senha())).thenReturn("senha-criptografada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        UsuarioResponseDTO resultado = usuarioService.criar(dto);

        assertNotNull(resultado);
        assertEquals(dto.email(), resultado.email());
        assertEquals(Role.PACIENTE, resultado.role());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaCadastrado() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("existente@teste.com", "123456", Role.PACIENTE);

        when(usuarioRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(EmailJaCadastradoException.class, () -> usuarioService.criar(dto));

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
