package br.com.juhmaran.petshop_api.api.products.dtos;

import br.com.juhmaran.petshop_api.api.products.dimensions.dtos.DimensionsResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Schema(name = "ProductResponse", description = "Representação de um produto")
public class ProductResponse {

    @Schema(description = "Identificador do produto", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", example = "Ração para cachorro")
    private String name;

    @Schema(description = "Descrição do produto", example = "Ração para cachorro de porte médio")
    private String description;

    @Schema(description = "Preço do produto", example = "100.00")
    private BigDecimal price;

    @Schema(description = "Quantidade em estoque", example = "10")
    private Integer quantityInStock;

    @Schema(description = "Categoria do produto", example = "Ração")
    private String category;

    @Schema(description = "Data de validade do produto", example = "01/01/2023")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate expirationDate;

    @Schema(description = "Fornecedor do produto", example = "PetShop Fornecedor")
    private String supplier;

    @Schema(description = "Código de barras do produto", example = "1234567890123")
    private String barcode;

    @Schema(description = "Peso do produto", example = "1.5")
    private Double weight;

    @Schema(description = "Dimensões do produto", implementation = DimensionsResponse.class)
    private DimensionsResponse dimensions;

}
