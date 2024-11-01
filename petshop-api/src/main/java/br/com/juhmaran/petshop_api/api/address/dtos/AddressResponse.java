package br.com.juhmaran.petshop_api.api.address.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AddressResponse", description = "Endereço")
public class AddressResponse {

    @Schema(name = "id", description = "ID", example = "1")
    private Long id;

    @Schema(name = "zipCode", description = "CEP", example = "01001-000")
    private String zipCode;

    @Schema(name = "street", description = "Logradouro", example = "Praça da Sé")
    private String street;

    @Schema(name = "neighborhood", description = "Bairro", example = "Sé")
    private String neighborhood;

    @Schema(name = "city", description = "Cidade", example = "São Paulo")
    private String city;

    @Schema(name = "complement", description = "Complemento", example = "lado ímpar")
    private String complement;

    @Schema(name = "unit", description = "Unidade", example = "Bloco A")
    private String unit;

    @Schema(name = "state", description = "Estado", example = "SP")
    private String state;

    @Schema(name = "country", description = "País", example = "Brasil")
    private String country;

    @Schema(name = "userId", description = "ID do usuário", example = "1")
    private Long userId;


}
