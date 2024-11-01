package br.com.juhmaran.petshop_api.api.auth.password.entities;

import br.com.juhmaran.petshop_api.core.validator.annotations.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordEntity {

    @NotBlank(message = "A senha antiga é obrigatória.")
    private String oldPassword;

    @ValidPassword
    @NotBlank(message = "A nova senha é obrigatória.")
    private String newPassword;

    @NotBlank(message = "A confirmação da senha é obrigatória.")
    private String confirmPassword;

}
