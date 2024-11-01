package br.com.juhmaran.petshop_api.api.common.models;

import br.com.juhmaran.petshop_api.api.common.enums.ProfessionalInfo;
import br.com.juhmaran.petshop_api.api.common.enums.SchedulingStatus;
import br.com.juhmaran.petshop_api.api.common.enums.ServiceType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Scheduling {

    private Long id;
    private LocalDateTime schedulingDateTime;
    private ServiceType serviceType;
    private ProfessionalInfo professionalInfo;
    private String consultationReason;
    private String additionalNotes;
    private SchedulingStatus status;

}
