package br.com.juhmaran.petshop_api.api.common.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class User {

    private Long id;

    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
    private String name;

    @CPF(message = "CPF inválido")
    private String cpf;

    @Email(message = "E-mail inválido")
    @Size(max = 150, message = "O e-mail deve ter no {max} caracteres.")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Senha inválida.")
    private String password;

    @Pattern(regexp = "^\\+?\\d{9,14}$", message = "O telefone é inválido.")
    private String phone;

    private Boolean enabled = true;

    private Set<Pet> pets;

    private Set<Role> roles = new HashSet<>();

    private List<Purchase> purchases;

    private List<Address> addresses;

}
