package br.com.juhmaran.petshop_api.api.agenda.dto;

import br.com.juhmaran.petshop_api.api.common.enums.Species;
import br.com.juhmaran.petshop_api.api.pet.validators.ValidGender;
import br.com.juhmaran.petshop_api.api.pet.validators.ValidSpecies;
import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PetData {

    @Schema(name = "name", maxLength = 150, requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nome do Pet.", example = "Rex")
    @NotNull(message = "Nome é obrigatório.")
    @Size(max = 150, message = "Nome deve ter no máximo {max} caracteres")
    private String name;

    @Schema(name = "species", requiredMode = Schema.RequiredMode.REQUIRED,
            ref = "Species", description = "Espécie do Pet.", example = "DOG")
    @ValidSpecies(message = "Espécie inválida.")
    @NotNull(message = "Espécie é obrigatória.")
    private Species species;

    @Schema(name = "breed", maxLength = 100, description = "Raça do Pet.", example = "Golden Retriever")
    @Size(max = 100, message = "Raça deve ter até {max} caracteres")
    private String breed;

    @Schema(name = "birthDate", description = "Data de nascimento do Pet.", example = "2021-01-01")
    @PastOrPresent(message = "Data de nascimento inválida.")
    private LocalDate birthDate;

    @Schema(name = "color", maxLength = 50, description = "Cor do Pet.", example = "Marrom")
    @Size(max = 50, message = "Cor deve ter até {max} caracteres")
    private String color;

    @Schema(name = "size", maxLength = 20, description = "Tamanho do Pet.", example = "Pequeno")
    @Size(max = 20, message = "Tamanho deve ter até {max} caracteres")
    private String size;

    @Schema(name = "gender", ref = "Gender", description = "Gênero do Pet.", example = "MALE")
    @ValidGender(message = "Gênero inválido.")
    private Gender gender;

    @Schema(name = "weight", minimum = "0", description = "Peso do Pet.", example = "5.5")
    @Min(value = 0, message = "O peso deve ser um número positivo.")
    @DecimalMin(value = "0.0", message = "O peso deve ser um número positivo.")
    @DecimalMax(value = "999.99", message = "O peso deve ser um número menor que 1000.")
    private double weight;

    @Schema(name = "healthHistory", maxLength = 500,
            description = "Histórico de saúde do Pet.", example = "Sem histórico de saúde.")
    private String healthHistory;

    @Schema(name = "age", minimum = "0", maximum = "30", description = "Idade do Pet.", example = "5")
    @Min(value = 0, message = "A idade deve ser um número positivo.")
    @Max(value = 30, message = "A idade deve ser um número menor que 31.")
    private int age;

}
