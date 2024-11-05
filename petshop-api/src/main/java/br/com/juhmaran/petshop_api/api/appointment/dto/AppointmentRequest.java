package br.com.juhmaran.petshop_api.api.appointment;

import br.com.juhmaran.petshop_api.core.customize.deserializer.CustomLocalDateDeserializer;
import br.com.juhmaran.petshop_api.core.customize.deserializer.CustomLocalTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {

    @NotNull(message = "O campo petId é obrigatório")
    private Long petId;

    @NotNull(message = "O campo date é obrigatório")
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate date;

    @NotNull(message = "O campo time é obrigatório")
    @JsonDeserialize(using = CustomLocalTimeDeserializer.class)
    private LocalTime time;

    @NotBlank(message = "O campo veterinarian é obrigatório")
    private String veterinarian;

    private String description;

}
