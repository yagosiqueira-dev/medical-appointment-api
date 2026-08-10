package com.yagosiqueira.medical_appointment_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            EmailJaCadastradoException.class,
            CrmJaCadastradoException.class,
            CpfJaCadastradoException.class,
            HorarioIndisponivelException.class
    })
    public ResponseEntity<Map<String, Object>> handleConflict(RuntimeException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler({
            UsuarioNaoEncontradoException.class,
            MedicoNaoEncontradoException.class,
            PacienteNaoEncontradoException.class,
            EspecialidadeNaoEncontradaException.class,
            ConsultaNaoEncontradaException.class
    })
    public ResponseEntity<Map<String, Object>> handleNotFound(RuntimeException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DataConsultaInvalidaException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(RuntimeException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        String mensagens = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .reduce((a, b) -> a + " | " + b)
                .orElse("Erro de validação");

        return buildResponse(HttpStatus.BAD_REQUEST, mensagens);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String mensagem) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("erro", status.getReasonPhrase());
        body.put("mensagem", mensagem);

        return ResponseEntity.status(status).body(body);
    }
}