package br.com.juhmaran.petshop_api.api.contact.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO que representa o formulário de contato.
 *
 * @author Juliane Maran
 */
@Data
@Schema(name = "ContactFormRequest", description = "Formulário de contato")
public class ContactFormRequest {

    @Schema(description = "Nome do usuário", example = "João da Silva")
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Schema(description = "Email do usuário", example = "joao.silva@email.com")
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @Schema(description = "Mensagem do usuário", example = "Olá, gostaria de mais informações sobre...")
    @NotBlank(message = "Mensagem é obrigatória")
    private String message;

}
