package br.com.juhmaran.petshop_api.api.auth.login.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Record que encapsula os dados de uma resposta de autenticação.
 *
 * @param authToken o token de autenticação
 * @param tokenType o tipo do token (por exemplo, Bearer)
 * @param expiresIn o tempo de expiração do token em milissegundos
 * @param username o nome de usuário associado ao token
 * @param name o nome completo do usuário
 * @param roles as funções (roles) atribuídas ao usuário
 */
@Schema(description = "Representa uma resposta de autenticação.", name = "TokenResponse")
public record TokenResponse(
        @Schema(description = "O token de autenticação.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String authToken,

        @Schema(description = "O tipo do token.", example = "Bearer")
        String tokenType,

        @Schema(description = "O tempo de expiração do token em milissegundos.", example = "3600")
        long expiresIn,

        @Schema(description = "O nome de usuário associado ao token.", example = "johndoe@email.com")
        String username,

        @Schema(description = "O nome completo do usuário.", example = "John Doe")
        String name,

        @Schema(description = "As funções (roles) atribuídas ao usuário.", example = "[\"ADMIN\", \"USER\"]")
        List<String> roles) {
}