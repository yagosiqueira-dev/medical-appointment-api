package com.yagosiqueira.medical_appointment_api.controller;

import com.yagosiqueira.medical_appointment_api.dto.DadosAutenticacao;
import com.yagosiqueira.medical_appointment_api.dto.DadosTokenJWT;
import com.yagosiqueira.medical_appointment_api.entity.Usuario;
import com.yagosiqueira.medical_appointment_api.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody DadosAutenticacao dados) {

        // 1. Converte o DTO em um token de autenticação que o Spring entende
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        // 2. O manager vai ao banco (através do nosso CustomUserDetailsService) e valida a senha
        var authentication = manager.authenticate(authenticationToken);

        // 3. Se a senha estiver correta, gera o JWT usando o usuário autenticado
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        // 4. Devolve o token encapsulado no nosso DTO de resposta
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
