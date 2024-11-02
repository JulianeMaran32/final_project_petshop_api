package br.com.juhmaran.petshop_api.api.auth.login.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Juliane Maran
 */
@Schema(description = "Representa uma resposta de autenticação, contendo o token de autenticação, " +
        "tipo de token, tempo de expiração, nome de usuário, nome completo e funções (roles) atribuídas ao usuário.",
        name = "TokenResponse")
@Data
@Builder
public class TokenResponse {

    @Schema(description = "O token de autenticação, utilizado para acessar recursos protegidos.",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String authToken;

    @Schema(description = "O tipo do token, geralmente 'Bearer'.", example = "Bearer")
    private String tokenType;

    @Schema(description = "O tempo de expiração do token em milissegundos, após o qual o token não será mais válido.",
            example = "3600")
    private long expiresIn;

    @Schema(description = "O nome de usuário associado ao token, geralmente o email do usuário.",
            example = "johndoe@email.com")
    private String username;

    @Schema(description = "O nome completo do usuário associado ao token.", example = "John Doe")
    private String name;

    @Schema(description = "As funções (roles) atribuídas ao usuário, que determinam suas permissões e acessos.")
    private List<String> roles;

}