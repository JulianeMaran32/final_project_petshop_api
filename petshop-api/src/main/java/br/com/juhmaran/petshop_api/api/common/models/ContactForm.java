package br.com.juhmaran.petshop_api.api.common.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactForm {

    @Size(min = 3, max = 100, message = "O nome deve ter entre {min} e {max} caracteres")
    private String name;

    @Email(message = "E-mail inválido")
    private String email;

    @Size(min = 3, max = 250, message = "O assunto deve ter entre {min} e {max} caracteres")
    private String message;

}
