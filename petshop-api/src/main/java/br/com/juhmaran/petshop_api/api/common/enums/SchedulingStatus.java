package br.com.juhmaran.petshop_api.api.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SchedulingStatus {

    SCHEDULED("Scheduled"), // Agendado
    CANCELED("Canceled"), // Cancelado
    COMPLETED("Completed"), // Concluído
    PENDING("Pending"), // Pendente
    CONFIRMED("Confirmed"), // Confirmado
    RESCHEDULED("Rescheduled"); // Reagendado

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
