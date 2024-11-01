package br.com.juhmaran.petshop_api.api.common.models;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permission {

    private Long id;

    @Size(min = 3, max = 50, message = "O nome deve ter entre {min} e {max} caracteres")
    private String name;

    @Size(min = 3, max = 250, message = "A descrição deve ter entre {min} e {max} caracteres")
    private String description;


}
