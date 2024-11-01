package br.com.juhmaran.petshop_api.api.auth.login.dtos;

import br.com.juhmaran.petshop_api.core.validator.annotations.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Record que encapsula os dados de uma solicitação de login.
 *
 * @param email    o email do usuário, que deve ser válido e não pode estar em branco
 * @param password a senha do usuário, que não pode estar em branco
 */
@Schema(description = "Representa uma solicitação de login.", name = "LoginRequest")
public record LoginRequest(
        @Schema(description = "O email do usuário.", example = "usuario@exemplo.com",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email é obrigatório.")
        @Email(message = "Email inválido.") String email,

        @Schema(description = "A senha do usuário.", example = "Senh@Fort3",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Senha é obrigatória.") @ValidPassword String password) {
}