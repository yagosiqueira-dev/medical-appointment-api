package com.yagosiqueira.medical_appointment_api.repository;

import com.yagosiqueira.medical_appointment_api.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByPacienteId(Long pacienteId);

    List<Consulta> findByMedicoId(Long medicoId);

    boolean existsByMedicoIdAndDataHora(Long medicoId, LocalDateTime dataHora);
}
