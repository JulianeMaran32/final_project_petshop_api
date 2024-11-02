package br.com.juhmaran.petshop_api.api.auth.login.dtos;

import br.com.juhmaran.petshop_api.core.validator.annotations.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Record que encapsula os dados de uma solicitação de login.
 *
 * @author Juliane Maran
 */
@Schema(description = "Representa uma solicitação de login.", name = "LoginRequest")
public record LoginRequest(
        @Schema(description = "O email do usuário. Deve ser um email válido. Não pode ser nulo ou vazio. Deve ser obrigatório.",
                example = "usuario@exemplo.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email é obrigatório.")
        @Email(message = "Email inválido.")
        String email,

        @Schema(description = "A senha do usuário. Deve ser uma senha válida. Não pode ser nulo ou vazio. " +
                "Deve ser obrigatório. A senha deve conter no mínimo 8 caracteres, sendo pelo menos um " +
                "caractere especial, um caractere numérico, um caractere minúsculo e um caractere maiúsculo.",
                example = "Senh@Fort3", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Senha é obrigatória.")
        @ValidPassword
        String password) {
}