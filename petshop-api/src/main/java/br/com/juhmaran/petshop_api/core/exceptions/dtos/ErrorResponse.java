package br.com.juhmaran.petshop_api.core.exceptions.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Juliane Maran
 */
@Getter
@AllArgsConstructor
@Schema(name = "ErrorResponse", description = "Classe que representa a resposta de erro.")
public class ErrorResponse {

    @Schema(name = "status", description = "Status HTTP.", example = "400")
    private int status;

    @Schema(name = "error", description = "Erro.", example = "Bad Request")
    private String error;

    @Schema(name = "message", description = "Mensagem de erro.", example = "Ocorreu um erro.")
    private String message;

}
