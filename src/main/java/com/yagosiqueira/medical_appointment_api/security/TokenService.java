package com.yagosiqueira.medical_appointment_api.security;

import com.yagosiqueira.medical_appointment_api.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    // Puxando os valores do application.yaml
    @Value("${jwt.secret}")
    private String secretString;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Transforma a nossa String do application.yaml em uma Chave Criptográfica
     * que o JJWT exige para assinar o token.
     */
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gera o token JWT para o usuário que acabou de fazer login.
     */
    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .subject(usuario.getEmail()) // O "dono" do token
                .issuedAt(new Date()) // Data de criação
                .expiration(new Date(System.currentTimeMillis() + expiration)) // Data de validade
                .signWith(getSecretKey()) // Assinatura digital
                .compact(); // Transforma tudo numa String final
    }

    /**
     * Lê o token recebido, valida a assinatura e devolve o email do usuário.
     */
    public String getSubject(String tokenJWT) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSecretKey()) // Confere se a assinatura é nossa mesma
                    .build()
                    .parseSignedClaims(tokenJWT) // Tenta abrir o token
                    .getPayload(); // Pega o corpo (dados) do token

            return claims.getSubject(); // Devolve o email

        } catch (Exception exception) {
            // Se o token for inválido, expirado ou fraudado, o JJWT lança exceção.
            // Aqui podemos lançar uma exceção nossa para o GlobalExceptionHandler tratar depois.
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
}