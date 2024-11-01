package br.com.juhmaran.petshop_api.api.agenda.dto;

import br.com.juhmaran.petshop_api.api.common.enums.ProfessionalInfo;
import br.com.juhmaran.petshop_api.api.common.enums.ServiceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(name = "AppointmentInfo", description = "Informações do Agendamento")
@Data
public class AppointmentInfo {

    @Schema(name = "appointmentDateTime", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Data e Hora do Agendamento", example = "2021-10-01 10:00")
    @NotNull(message = "A data e hora do agendamento devem ser informadas")
    @FutureOrPresent(message = "A data e hora do agendamento devem estar no presente ou futuro")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING, timezone = "UTC", locale = "pt-BR")
    private LocalDateTime appointmentDateTime;

    @Schema(name = "serviceType", requiredMode = Schema.RequiredMode.REQUIRED, ref = "ServiceType",
            description = "Tipo de Serviço/Consulta", example = "CONSULTATION")
    @NotNull(message = "O tipo de serviço deve ser informado")
    private ServiceType serviceType;

    @Schema(name = "professionalInfo", requiredMode = Schema.RequiredMode.REQUIRED, ref = "ProfessionalInfo",
            description = "Nome do Atendente/Veterinário", example = "VETERINARIAN")
    @NotNull(message = "As informações do profissional devem ser informadas")
    private ProfessionalInfo professionalInfo;

    @Schema(name = "consultationReason", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            maxLength = 500, description = "Motivo/Razão da Consulta", example = "Dor de Barriga")
    private String consultationReason;

    @Schema(name = "additionalNotes", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            maxLength = 500, description = "Notas Adicionais", example = "O cachorro está comendo bem")
    private String additionalNotes;

}
