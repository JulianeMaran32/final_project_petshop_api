package br.com.juhmaran.petshop_api.api.pet.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "PetStatisticsResponse",
        description = "Resposta de estatísticas de Pets. Retorna a quantidade de pets agrupados por Espécie, Raça, Gênero e status de castração")
public class PetStatisticsResponse {

    @Schema(name = "category", example = "DOG",
            description = "Categoria do agrupamento: Espécie, Raça, Gênero e status da castração")
    private String category;

    @Schema(name = "count", example = "2", description = "Quantidade de pets por agrupamento")
    private Long count;

}
