package com.yagosiqueira.medical_appointment_api.service;

import com.yagosiqueira.medical_appointment_api.dto.ConsultaRequestDTO;
import com.yagosiqueira.medical_appointment_api.dto.ConsultaResponseDTO;
import com.yagosiqueira.medical_appointment_api.entity.Consulta;
import com.yagosiqueira.medical_appointment_api.entity.Medico;
import com.yagosiqueira.medical_appointment_api.entity.Paciente;
import com.yagosiqueira.medical_appointment_api.enums.StatusConsulta;
import com.yagosiqueira.medical_appointment_api.exception.*;
import com.yagosiqueira.medical_appointment_api.repository.ConsultaRepository;
import com.yagosiqueira.medical_appointment_api.repository.MedicoRepository;
import com.yagosiqueira.medical_appointment_api.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(ConsultaRepository consultaRepository,
                           MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public ConsultaResponseDTO agendar(ConsultaRequestDTO dto) {
        if (dto.dataHora().isBefore(LocalDateTime.now())) {
            throw new DataConsultaInvalidaException();
        }

        Medico medico = medicoRepository.findById(dto.medicoId())
                .orElseThrow(() -> new MedicoNaoEncontradoException(dto.medicoId()));

        Paciente paciente = pacienteRepository.findById(dto.pacienteId())
                .orElseThrow(() -> new PacienteNaoEncontradoException(dto.pacienteId()));

        boolean horarioOcupado = consultaRepository
                .existsByMedicoIdAndDataHora(dto.medicoId(), dto.dataHora());

        if (horarioOcupado) {
            throw new HorarioIndisponivelException();
        }

        Consulta consulta = Consulta.builder()
                .medico(medico)
                .paciente(paciente)
                .dataHora(dto.dataHora())
                .status(StatusConsulta.AGENDADA)
                .build();

        Consulta consultaSalva = consultaRepository.save(consulta);

        return toResponseDTO(consultaSalva);
    }

    @Transactional
    public ConsultaResponseDTO cancelar(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException(id));

        consulta.setStatus(StatusConsulta.CANCELADA);

        Consulta consultaAtualizada = consultaRepository.save(consulta);

        return toResponseDTO(consultaAtualizada);
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponseDTO> listarPorPaciente(Long pacienteId) {
        return consultaRepository.findByPacienteId(pacienteId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponseDTO> listarPorMedico(Long medicoId) {
        return consultaRepository.findByMedicoId(medicoId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private ConsultaResponseDTO toResponseDTO(Consulta consulta) {
        return new ConsultaResponseDTO(
                consulta.getId(),
                consulta.getMedico().getNome(),
                consulta.getPaciente().getNome(),
                consulta.getDataHora(),
                consulta.getStatus()
        );
    }
}
