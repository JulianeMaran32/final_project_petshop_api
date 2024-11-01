package br.com.juhmaran.petshop_api.api.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProfessionalInfo {

    VETERINARIAN("Veterinarian"),
    ATTENDANT("Attendant"),
    GROOMER("Groomer"),
    TRAINER("Trainer"),
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