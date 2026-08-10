package com.yagosiqueira.medical_appointment_api.service;

import com.yagosiqueira.medical_appointment_api.dto.EspecialidadeRequestDTO;
import com.yagosiqueira.medical_appointment_api.dto.EspecialidadeResponseDTO;
import com.yagosiqueira.medical_appointment_api.entity.Especialidade;
import com.yagosiqueira.medical_appointment_api.exception.EspecialidadeNaoEncontradaException;
import com.yagosiqueira.medical_appointment_api.repository.EspecialidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository) {
        this.especialidadeRepository = especialidadeRepository;
    }

    @Transactional
    public EspecialidadeResponseDTO criar(EspecialidadeRequestDTO dto) {
        Especialidade especialidade = Especialidade.builder()
                .nome(dto.nome())
                .build();

        Especialidade especialidadeSalva = especialidadeRepository.save(especialidade);

        return toResponseDTO(especialidadeSalva);
    }

    @Transactional(readOnly = true)
    public List<EspecialidadeResponseDTO> listarTodas() {
        return especialidadeRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EspecialidadeResponseDTO buscarPorId(Long id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new EspecialidadeNaoEncontradaException(id));

        return toResponseDTO(especialidade);
    }

    private EspecialidadeResponseDTO toResponseDTO(Especialidade especialidade) {
        return new EspecialidadeResponseDTO(
                especialidade.getId(),
                especialidade.getNome()
        );
    }
}
