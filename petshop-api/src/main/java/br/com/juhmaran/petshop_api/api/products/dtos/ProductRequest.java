package br.com.juhmaran.petshop_api.api.products.dtos;

import br.com.juhmaran.petshop_api.api.products.dimensions.dtos.DimensionsRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Schema(name = "ProductRequest", description = "Representação do produto")
public class ProductRequest {

    @Schema(description = "Nome do produto", example = "Ração para cachorro")
    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do produto deve ter entre {min} e {max} caracteres")
    private String name;

    @Schema(description = "Descrição do produto", example = "Ração para cachorro de porte médio")
    @Size(min = 3, max = 255, message = "A descrição do produto deve ter entre {min} e {max} caracteres")
    private String description;

    @Schema(description = "Preço do produto", example = "100.00")
    @NotNull(message = "O preço do produto é obrigatório")
    @PositiveOrZero(message = "O preço do produto deve ser positivo")
    @Digits(integer = 10, fraction = 2,
            message = "O preço do produto deve ter no máximo {integer} dígitos inteiros e {faction} dígitos fracionários")
    private BigDecimal price;

    @Schema(description = "Quantidade em estoque", example = "10")
    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Min(value = 1, message = "A quantidade em estoque deve ser no mínimo {value}")
    private Integer quantityInStock;

    @Schema(description = "Categoria do produto", example = "Ração")
    @NotBlank(message = "A categoria do produto é obrigatória")
    private String category;

    @Schema(description = "Data de validade do produto", example = "01/01/2023")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Future(message = "A data de validade deve ser no futuro")
    private LocalDate expirationDate;

    @Schema(description = "Fornecedor do produto", example = "PetShop Fornecedor")
    private String supplier;

    @Schema(description = "Código de barras do produto", example = "1234567890123")
    private String barcode;

    @Schema(description = "Peso do produto", example = "1.5")
    @PositiveOrZero(message = "O peso do produto deve ser positivo")
    @Digits(integer = 10, fraction = 2,
            message = "O peso do produto deve ter no máximo {integer} dígitos inteiros e {faction} dígitos fracionários")
    private Double weight;

    @Schema(description = "Dimensões do produto", implementation = DimensionsRequest.class)
    private DimensionsRequest dimensions;

}
