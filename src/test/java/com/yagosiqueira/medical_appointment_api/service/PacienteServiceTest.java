package com.yagosiqueira.medical_appointment_api.service;

import com.yagosiqueira.medical_appointment_api.dto.PacienteRequestDTO;
import com.yagosiqueira.medical_appointment_api.dto.PacienteResponseDTO;
import com.yagosiqueira.medical_appointment_api.entity.Paciente;
import com.yagosiqueira.medical_appointment_api.entity.Usuario;
import com.yagosiqueira.medical_appointment_api.enums.Role;
import com.yagosiqueira.medical_appointment_api.exception.CpfJaCadastradoException;
import com.yagosiqueira.medical_appointment_api.repository.PacienteRepository;
import com.yagosiqueira.medical_appointment_api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    void deveCriarPacienteComSucesso() {
        PacienteRequestDTO dto = new PacienteRequestDTO(
                "Beatriz", "123.456.789-00", LocalDate.of(1995, 5, 20), 1L
        );

        Usuario usuario = Usuario.builder().id(1L).email("bia@teste.com").role(Role.PACIENTE).build();

        Paciente pacienteSalvo = Paciente.builder()
                .id(1L)
                .nome("Beatriz")
                .cpf("123.456.789-00")
                .dataNascimento(dto.dataNascimento())
                .usuario(usuario)
                .build();

        when(pacienteRepository.existsByCpf(dto.cpf())).thenReturn(false);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteSalvo);

        PacienteResponseDTO resultado = pacienteService.criar(dto);

        assertNotNull(resultado);
        assertEquals("Beatriz", resultado.nome());
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaCadastrado() {
        PacienteRequestDTO dto = new PacienteRequestDTO(
                "Beatriz", "123.456.789-00", LocalDate.of(1995, 5, 20), 1L
        );

        when(pacienteRepository.existsByCpf(dto.cpf())).thenReturn(true);

        assertThrows(CpfJaCadastradoException.class, () -> pacienteService.criar(dto));

        verify(usuarioRepository, never()).findById(anyLong());
    }
}
