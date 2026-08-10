package com.yagosiqueira.medical_appointment_api.repository;

import com.yagosiqueira.medical_appointment_api.entity.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

    Optional<Especialidade> findByNome(String nome);
}
