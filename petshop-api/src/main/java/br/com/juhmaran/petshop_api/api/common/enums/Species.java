package br.com.juhmaran.petshop_api.api.common.enums;

import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

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
    public static ProfessionalInfo fromString(String value) {
        for (ProfessionalInfo professionalInfo : ProfessionalInfo.values()) {
            if (professionalInfo.name().equalsIgnoreCase(value)) {
                return professionalInfo;
            }
        }
        throw new PetShopInternalServerErrorException("Dados inválidos.");
    }

}
