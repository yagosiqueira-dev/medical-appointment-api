package com.yagosiqueira.medical_appointment_api.service;

import com.yagosiqueira.medical_appointment_api.dto.ConsultaRequestDTO;
import com.yagosiqueira.medical_appointment_api.entity.Medico;
import com.yagosiqueira.medical_appointment_api.entity.Paciente;
import com.yagosiqueira.medical_appointment_api.exception.DataConsultaInvalidaException;
import com.yagosiqueira.medical_appointment_api.exception.HorarioIndisponivelException;
import com.yagosiqueira.medical_appointment_api.repository.ConsultaRepository;
import com.yagosiqueira.medical_appointment_api.repository.MedicoRepository;
import com.yagosiqueira.medical_appointment_api.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private ConsultaService consultaService;

    @Test
    void deveLancarExcecaoQuandoHorarioJaOcupado() {
        LocalDateTime dataFutura = LocalDateTime.now().plusDays(1);

        ConsultaRequestDTO dto = new ConsultaRequestDTO(1L, 1L, dataFutura);

        Medico medico = Medico.builder().id(1L).nome("Dr. João").build();
        Paciente paciente = Paciente.builder().id(1L).nome("Beatriz").build();

        when(medicoRepository.findById(1L)).thenReturn(Optional.of(medico));
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(consultaRepository.existsByMedicoIdAndDataHora(1L, dataFutura)).thenReturn(true);

        assertThrows(HorarioIndisponivelException.class, () -> consultaService.agendar(dto));

        verify(consultaRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoDataNoPassado() {
        LocalDateTime dataPassada = LocalDateTime.now().minusDays(1);

        ConsultaRequestDTO dto = new ConsultaRequestDTO(1L, 1L, dataPassada);

        assertThrows(DataConsultaInvalidaException.class, () -> consultaService.agendar(dto));

        verify(medicoRepository, never()).findById(anyLong());
        verify(consultaRepository, never()).save(any());
    }
}
