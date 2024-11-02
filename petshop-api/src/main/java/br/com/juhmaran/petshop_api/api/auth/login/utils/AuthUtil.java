package br.com.juhmaran.petshop_api.api.auth.login.utils;

import br.com.juhmaran.petshop_api.api.auth.login.dtos.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AuthUtil {

    private final AuthenticationManager authenticationManager;

    public Authentication authenticateUser(LoginRequest loginRequest) {
        log.info("Autenticando usuário com email: {}", loginRequest.email());
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
    }

}
