package br.com.juhmaran.petshop_api.api.address.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ViaCepResponse", description = "Resposta da API ViaCEP")
public class ViaCepResponse {

    @Schema(name = "cep", description = "CEP", example = "01001-000")
    private String cep;

    @Schema(name = "logradouro", description = "Logradouro", example = "Praça da Sé")
    private String logradouro;

    @Schema(name = "bairro", description = "Bairro", example = "Sé")
    private String bairro;

    @Schema(name = "localidade", description = "Cidade", example = "São Paulo")
    private String localidade;

    @Schema(name = "uf", description = "Estado", example = "SP")
    private String uf;

}
