package com.yagosiqueira.medical_appointment_api.service;

import com.yagosiqueira.medical_appointment_api.dto.EspecialidadeRequestDTO;
import com.yagosiqueira.medical_appointment_api.dto.EspecialidadeResponseDTO;
import com.yagosiqueira.medical_appointment_api.entity.Especialidade;
import com.yagosiqueira.medical_appointment_api.exception.EspecialidadeNaoEncontradaException;
import com.yagosiqueira.medical_appointment_api.repository.EspecialidadeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EspecialidadeServiceTest {

    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @InjectMocks
    private EspecialidadeService especialidadeService;

    @Test
    void deveCriarEspecialidadeComSucesso() {
        EspecialidadeRequestDTO dto = new EspecialidadeRequestDTO("Cardiologia");

        Especialidade especialidadeSalva = Especialidade.builder()
                .id(1L)
                .nome("Cardiologia")
                .build();

        when(especialidadeRepository.save(any(Especialidade.class))).thenReturn(especialidadeSalva);

        EspecialidadeResponseDTO resultado = especialidadeService.criar(dto);

        assertNotNull(resultado);
        assertEquals("Cardiologia", resultado.nome());
    }

    @Test
    void deveLancarExcecaoQuandoEspecialidadeNaoEncontrada() {
        when(especialidadeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EspecialidadeNaoEncontradaException.class,
                () -> especialidadeService.buscarPorId(99L));
    }
}
