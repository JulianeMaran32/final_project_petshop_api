package br.com.juhmaran.petshop_api.api.common.enums;

import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public enum Species {

    DOG("Dog"),
    CAT("Cat"),
    BIRD("Bird"),
    FISH("Fish"),
    REPTILE("Reptile"),
    RABBIT("Rabbit"),
    OTHER("Other");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Species fromString(String value) {
        for (Species species : Species.values()) {
            if (species.name().equalsIgnoreCase(value)) {
                return species;
            }
        }
        log.error("Valor inválido para Espécie: {}", value);
        throw new PetShopInternalServerErrorException("[Species] - Dados inválidos.");
    }

}
