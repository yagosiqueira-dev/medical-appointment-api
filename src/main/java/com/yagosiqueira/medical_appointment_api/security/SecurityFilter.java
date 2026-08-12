package com.yagosiqueira.medical_appointment_api.security; // Ajuste para o seu pacote

import com.yagosiqueira.medical_appointment_api.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            // 1. Valida o token e pega o email
            var subject = tokenService.getSubject(tokenJWT);

            // 2. Busca o usuário no banco
            var usuario = repository.findByEmail(subject)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado no token."));

            // 3. Cria o "crachá" do Spring Security com as permissões (Roles) do usuário
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            // 4. Força a autenticação no contexto atual
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Segue o fluxo para o Controller
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
