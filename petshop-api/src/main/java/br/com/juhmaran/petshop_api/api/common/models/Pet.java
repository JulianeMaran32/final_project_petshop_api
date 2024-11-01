package br.com.juhmaran.petshop_api.api.common.models;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.api.common.enums.Species;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Pet {

    private Long id;

    @Size(min = 3, max = 100, message = "O nome deve ter entre {min} e {max} caracteres")
    private String name;

    private Species species;

    @Size(min = 3, max = 50, message = "A raça deve ter entre {min} e {max} caracteres")
    private String breed;

    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate birthDate;

    @Size(min = 3, max = 25, message = "A cor deve ter entre {min} e {max} caracteres")
    private String color;

    @Size(min = 3, max = 25, message = "O tamanho deve ter entre {min} e {max} caracteres")
    private String size;

    private Gender gender;

    private Boolean castrated;

    @DecimalMin(value = "0.1", message = "O peso deve ser maior que 0.1")
    @DecimalMax(value = "1000.0", message = "O peso deve ser menor que 1000.0")
    private Double weight;

    @Size(min = 3, max = 250, message = "O histórico de saúde deve ter entre {min} e {max} caracteres")
    private String healthHistory;

    @Max(value = 30, message = "A idade deve ser menor ou igual a 30")
    @Min(value = 0, message = "A idade deve ser maior ou igual a 0")
    private int age;

}
