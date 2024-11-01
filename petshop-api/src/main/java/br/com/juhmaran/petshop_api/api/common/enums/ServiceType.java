package br.com.juhmaran.petshop_api.api.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@Schema(description = "Enumeração que representa os diferentes tipos de serviços disponíveis no sistema.",
        enumAsRef = true, example = "GROOMING",
        allowableValues = {"BATH", "GROOMING", "BATH_GROOMING", "CONSULTATION", "VACCINATION", "OTHER"})
@AllArgsConstructor
public enum ServiceType {

    BATH("Bath"),
    GROOMING("Grooming"),
    BATH_GROOMING("Bath and Grooming"),
    CONSULTATION("Consultation"),
    VACCINATION("Vaccination"),
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
        throw new IllegalArgumentException();
    }

}