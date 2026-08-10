package com.yagosiqueira.medical_appointment_api.service;

import com.yagosiqueira.medical_appointment_api.dto.MedicoRequestDTO;
import com.yagosiqueira.medical_appointment_api.dto.MedicoResponseDTO;
import com.yagosiqueira.medical_appointment_api.entity.Especialidade;
import com.yagosiqueira.medical_appointment_api.entity.Medico;
import com.yagosiqueira.medical_appointment_api.entity.Usuario;
import com.yagosiqueira.medical_appointment_api.exception.CrmJaCadastradoException;
import com.yagosiqueira.medical_appointment_api.exception.EspecialidadeNaoEncontradaException;
import com.yagosiqueira.medical_appointment_api.exception.MedicoNaoEncontradoException;
import com.yagosiqueira.medical_appointment_api.exception.UsuarioNaoEncontradoException;
import com.yagosiqueira.medical_appointment_api.repository.EspecialidadeRepository;
import com.yagosiqueira.medical_appointment_api.repository.MedicoRepository;
import com.yagosiqueira.medical_appointment_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public MedicoService(MedicoRepository medicoRepository,
                         UsuarioRepository usuarioRepository,
                         EspecialidadeRepository especialidadeRepository) {
        this.medicoRepository = medicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    @Transactional
    public MedicoResponseDTO criar(MedicoRequestDTO dto) {
        if (medicoRepository.existsByCrm(dto.crm())) {
            throw new CrmJaCadastradoException(dto.crm());
        }

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(dto.usuarioId()));

        Set<Especialidade> especialidades = buscarEspecialidades(dto.especialidadeIds());

        Medico medico = Medico.builder()
                .nome(dto.nome())
                .crm(dto.crm())
                .usuario(usuario)
                .especialidades(especialidades)
                .build();

        Medico medicoSalvo = medicoRepository.save(medico);

        return toResponseDTO(medicoSalvo);
    }

    @Transactional(readOnly = true)
    public MedicoResponseDTO buscarPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new MedicoNaoEncontradoException(id));

        return toResponseDTO(medico);
    }

    @Transactional(readOnly = true)
    public List<MedicoResponseDTO> listarTodos() {
        return medicoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private Set<Especialidade> buscarEspecialidades(List<Long> especialidadeIds) {
        if (especialidadeIds == null || especialidadeIds.isEmpty()) {
            return Set.of();
        }

        return especialidadeIds.stream()
                .map(id -> especialidadeRepository.findById(id)
                        .orElseThrow(() -> new EspecialidadeNaoEncontradaException(id)))
                .collect(Collectors.toSet());
    }

    private MedicoResponseDTO toResponseDTO(Medico medico) {
        List<String> nomesEspecialidades = medico.getEspecialidades()
                .stream()
                .map(Especialidade::getNome)
                .collect(Collectors.toList());

        return new MedicoResponseDTO(
                medico.getId(),
                medico.getNome(),
                medico.getCrm(),
                nomesEspecialidades
        );
    }
}
