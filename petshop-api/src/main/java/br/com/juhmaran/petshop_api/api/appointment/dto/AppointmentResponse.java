package br.com.juhmaran.petshop_api.api.appointment;

import br.com.juhmaran.petshop_api.core.customize.serializer.CustomLocalDateSerializer;
import br.com.juhmaran.petshop_api.core.customize.serializer.CustomLocalTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

    private Long id;

    private Long petId;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate date;

    @JsonSerialize(using = CustomLocalTimeSerializer.class)
    private LocalTime time;

    private String veterinarian;

    private String description;

}
