package br.com.juhmaran.petshop_api.api.pet.dtos;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.api.common.enums.Species;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {

    @Schema(name = "id", description = "ID do Pet.", example = "1")
    private Long id;

    @Schema(name = "name", description = "Nome do Pet.", example = "Rex")
    private String name;

    @Schema(name = "species", description = "Espécie do Pet.", example = "DOG")
    private Species species;

    @Schema(name = "breed", description = "Raça do Pet.", example = "Golden Retriever")
    private String breed;

    @Schema(name = "birthDate", description = "Data de nascimento do Pet.", example = "2021-01-01")
    private LocalDate birthDate;

    @Schema(name = "color", description = "Cor do Pet.", example = "Marrom")
    private String color;

    @Schema(name = "size", description = "Tamanho do Pet.", example = "Pequeno")
    private String size;

    @Schema(name = "gender", description = "Gênero do Pet.", example = "MALE")
    private Gender gender;

    @Schema(name = "castrated", description = "Se o Pet é castrado.", example = "true")
    private Boolean castrated;

    @Schema(name = "weight", description = "Peso do Pet.", example = "5.5")
    private double weight;

    @Schema(name = "healthHistory", description = "Histórico de saúde do Pet.", example = "Sem histórico de saúde.")
    private String healthHistory;

    @Schema(name = "age", description = "Idade do Pet.", example = "2")
    private int age;

    @Schema(name = "userId", description = "ID do usuário dono do Pet.", example = "1")
    private Long userId;

    public PetResponse(Long id, String name, Species species, Gender gender, Boolean castrated) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.gender = gender;
        this.castrated = castrated;
    }

}
