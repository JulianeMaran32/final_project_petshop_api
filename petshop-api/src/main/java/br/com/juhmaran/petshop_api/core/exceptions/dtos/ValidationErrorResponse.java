package br.com.juhmaran.petshop_api.core.exceptions.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Map;

/**
 * @author Juliane Maran
 */
@Getter
@Schema(name = "ValidationErrorResponse", description = "Classe que representa a resposta de erro de validação.")
public class ValidationErrorResponse extends ErrorResponse {

    @Schema(name = "errors", description = "Erros de validação.", example = "{\"name\": \"Nome é obrigatório.\"}")
    private final Map<String, String> errors;

    public ValidationErrorResponse(int status, String error, String message, Map<String, String> errors) {
        super(status, error, message);
        this.errors = errors;
    }

}
