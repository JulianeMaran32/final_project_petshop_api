package br.com.juhmaran.petshop_api.api.auth.login.controllers;

import br.com.juhmaran.petshop_api.api.auth.login.dtos.LoginRequest;
import br.com.juhmaran.petshop_api.api.auth.login.dtos.TokenResponse;
import br.com.juhmaran.petshop_api.api.auth.login.services.AuthService;
import br.com.juhmaran.petshop_api.api.user.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operações relacionadas a Autenticação")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("Recebida solicitação de login para o email: {}", loginRequest.email());
        TokenResponse response = authService.login(loginRequest);
        log.info("Login bem-sucedido para o email: {}", loginRequest.email());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getCurrentUser() {
        log.info("Recebida solicitação para obter o usuário autenticado.");
        UserResponse response = authService.getCurrentUser();
        log.info("Usuário autenticado obtido com sucesso: {}", response.getEmail());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}