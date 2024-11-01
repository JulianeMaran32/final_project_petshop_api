package br.com.juhmaran.petshop_api.api.common.models;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dimensions {

    @DecimalMax(value = "999.99", message = "A altura deve ser menor que 1000")
    @DecimalMin(value = "0.01", message = "A altura deve ser maior que zero")
    private Double height;

    @DecimalMax(value = "999.99", message = "A largura deve ser menor que 1000")
    @DecimalMin(value = "0.01", message = "A largura deve ser maior que zero")
    private Double width;

    @DecimalMax(value = "999.99", message = "A profundidade deve ser menor que 1000")
    @DecimalMin(value = "0.01", message = "A profundidade deve ser maior que zero")
    private Double depth;

}
