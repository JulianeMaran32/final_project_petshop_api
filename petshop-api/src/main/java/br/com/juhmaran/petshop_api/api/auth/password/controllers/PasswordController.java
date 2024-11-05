package br.com.juhmaran.petshop_api.api.auth.password.controllers;

import br.com.juhmaran.petshop_api.api.auth.password.services.PasswordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Password", description = "Operações relacionadas a Redefinição de Senha")
public class PasswordController {

    private final PasswordService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam(value = "email") String email) {
        passwordResetService.createPasswordResetTokenForUser(email);
        return ResponseEntity.status(HttpStatus.OK).body("Token de redefinição de senha enviado para e-mail.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "new_password") String newPassword,
            @RequestParam(value = "confirm_password") String confirmPassword) {
        if (passwordResetService.validatePasswordResetToken(token)) {
            passwordResetService.resetPassword(token, newPassword, confirmPassword);
            return ResponseEntity.status(HttpStatus.OK).body("Senha redefinida com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido ou expirado.");
    }

}