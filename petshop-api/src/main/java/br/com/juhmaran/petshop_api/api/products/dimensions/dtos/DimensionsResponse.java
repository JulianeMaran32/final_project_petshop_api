package br.com.juhmaran.petshop_api.api.products.dimensions.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "DimensionsResponse", description = "Representação das dimensões do produto")
public class DimensionsResponse {

    @Schema(description = "Altura do produto", example = "10.0")
    private Double height;

    @Schema(description = "Largura do produto", example = "10.0")
    private Double width;

    @Schema(description = "Profundidade do produto", example = "10.0")
    private Double depth;

}
