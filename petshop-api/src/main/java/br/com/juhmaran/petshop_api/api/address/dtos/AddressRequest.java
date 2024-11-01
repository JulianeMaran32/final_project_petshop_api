package br.com.juhmaran.petshop_api.api.address.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AddressRequest", description = "Requisição de endereço")
public class AddressRequest {

    @Schema(name = "zipCode", pattern = "\\d{8}", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "CEP", example = "01001000")
    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP inválido")
    private String zipCode;

    @Schema(name = "street", minLength = 3, requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Logradouro", example = "Praça da Sé")
    @NotBlank(message = "Rua é obrigatória")
    @Size(min = 3, message = "Rua deve ter no mínimo {min} caracteres")
    private String street;

    @Schema(name = "neighborhood", minLength = 3, requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Bairro", example = "Centro")
    @NotBlank(message = "Bairro é obrigatório")
    @Size(min = 3, message = "Bairro deve ter no mínimo {min} caracteres")
    private String neighborhood;

    @Schema(name = "city", minLength = 3, requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Cidade", example = "São Paulo")
    @NotBlank(message = "Cidade é obrigatória")
    @Size(min = 3, message = "Cidade deve ter no mínimo {min} caracteres")
    private String city;

    @Schema(name = "complement", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Complemento", example = "lado ímpar")
    private String complement;

    @Schema(name = "unit", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Unidade", example = "Bloco A")
    private String unit;

    @Schema(name = "state", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Estado", example = "SP")
    @Pattern(regexp = "[A-Z]{2}", message = "Estado inválido")
    private String state;

    @Schema(name = "country", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "País", example = "Brasil")
    private String country;

    @Schema(name = "userId", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "ID do usuário", example = "1")
    private Long userId;

}
