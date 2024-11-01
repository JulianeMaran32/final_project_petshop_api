package br.com.juhmaran.petshop_api.api.common.models;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private Long id;

    @Pattern(regexp = "\\d{8}", message = "CEP inválido")
    private String zipCode; // CEP

    @Size(min = 3, message = "Rua deve ter no mínimo {min} caracteres")
    private String street; // Logradouro

    private String number; // Número

    @Size(min = 3, message = "Bairro deve ter no mínimo {min} caracteres")
    private String neighborhood; // Bairro

    @Size(min = 3, message = "Cidade deve ter no mínimo {min} caracteres")
    private String city; // Cidade

    private String complement; // Complemento

    private String unit; // Unidade

    @Pattern(regexp = "[A-Z]{2}", message = "Estado inválido")
    private String state; // Estado / UF

    private String country; // País

}
