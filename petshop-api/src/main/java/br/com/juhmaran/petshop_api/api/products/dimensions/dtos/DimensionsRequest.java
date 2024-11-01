package br.com.juhmaran.petshop_api.api.products.dimensions.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "DimensionsRequest", description = "Representação das dimensões do produto")
public class DimensionsRequest {

    @Schema(description = "Altura do produto", example = "10.0")
    @Digits(integer = 3, fraction = 2,
            message = "A altura do produto deve ter no máximo {integer} dígitos inteiros e {fraction} dígitos fracionários")
    @PositiveOrZero(message = "A altura do produto deve ser positiva")
    private Double height;

    @Schema(description = "Largura do produto", example = "10.0")
    @Digits(integer = 3, fraction = 2,
            message = "A largura do produto deve ter no máximo {integer} dígitos inteiros e {fraction} dígitos fracionários")
    @PositiveOrZero(message = "A largura do produto deve ser positiva")
    private Double width;

    @Schema(description = "Profundidade do produto", example = "10.0")
    @Digits(integer = 3, fraction = 2,
            message = "A profundidade do produto deve ter no máximo {integer} dígitos inteiros e {fraction} dígitos fracionários")
    @PositiveOrZero(message = "A profundidade do produto deve ser positiva")
    private Double depth;

}
