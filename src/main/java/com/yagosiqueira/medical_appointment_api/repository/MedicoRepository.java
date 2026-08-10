package com.yagosiqueira.medical_appointment_api.repository;

import com.yagosiqueira.medical_appointment_api.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Optional<Medico> findByCrm(String crm);

    boolean existsByCrm(String crm);
}
