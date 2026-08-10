package com.yagosiqueira.medical_appointment_api.service;

import com.yagosiqueira.medical_appointment_api.dto.PacienteRequestDTO;
import com.yagosiqueira.medical_appointment_api.dto.PacienteResponseDTO;
import com.yagosiqueira.medical_appointment_api.entity.Paciente;
import com.yagosiqueira.medical_appointment_api.entity.Usuario;
import com.yagosiqueira.medical_appointment_api.exception.CpfJaCadastradoException;
import com.yagosiqueira.medical_appointment_api.exception.PacienteNaoEncontradoException;
import com.yagosiqueira.medical_appointment_api.exception.UsuarioNaoEncontradoException;
import com.yagosiqueira.medical_appointment_api.repository.PacienteRepository;
import com.yagosiqueira.medical_appointment_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;

    public PacienteService(PacienteRepository pacienteRepository, UsuarioRepository usuarioRepository) {
        this.pacienteRepository = pacienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public PacienteResponseDTO criar(PacienteRequestDTO dto) {
        if (pacienteRepository.existsByCpf(dto.cpf())) {
            throw new CpfJaCadastradoException(dto.cpf());
        }

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(dto.usuarioId()));

        Paciente paciente = Paciente.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .dataNascimento(dto.dataNascimento())
                .usuario(usuario)
                .build();

        Paciente pacienteSalvo = pacienteRepository.save(paciente);

        return toResponseDTO(pacienteSalvo);
    }

    @Transactional(readOnly = true)
    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException(id));

        return toResponseDTO(paciente);
    }

    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private PacienteResponseDTO toResponseDTO(Paciente paciente) {
        return new PacienteResponseDTO(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getDataNascimento()
        );
    }
}
