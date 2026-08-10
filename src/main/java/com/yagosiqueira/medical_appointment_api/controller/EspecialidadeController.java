package com.yagosiqueira.medical_appointment_api.controller;

import com.yagosiqueira.medical_appointment_api.dto.EspecialidadeRequestDTO;
import com.yagosiqueira.medical_appointment_api.dto.EspecialidadeResponseDTO;
import com.yagosiqueira.medical_appointment_api.service.EspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    public EspecialidadeController(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }

    @PostMapping
    public ResponseEntity<EspecialidadeResponseDTO> criar(@Valid @RequestBody EspecialidadeRequestDTO dto) {
        EspecialidadeResponseDTO especialidadeCriada = especialidadeService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadeCriada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(especialidadeService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadeResponseDTO>> listarTodas() {
        return ResponseEntity.ok(especialidadeService.listarTodas());
    }
}
