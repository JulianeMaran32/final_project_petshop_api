package br.com.juhmaran.petshop_api.api.user.dto.request;

import br.com.juhmaran.petshop_api.core.validator.annotations.ValidPhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@Schema(name = "UserRegistrationRequest", description = "Requisição de cadastro de usuário.")
public class UserUpdateRequest {

    @Schema(name = "name", minLength = 3, maxLength = 150,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nome do usuário.", example = "Juliane Maran")
    @Size(min = 3, max = 150, message = "O nome deve ter entre {min} e {max} caracteres.")
    private String name;

    @Schema(name = "cpf", pattern = "^[0-9]{11}$", minLength = 11, maxLength = 11,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Número do CPF do usuário", example = "12345678901")
    @CPF(message = "O CPF é inválido.")
    private String cpf;

    @Schema(name = "email", format = "email", maxLength = 150,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Email do usuário.", example = "usuario@email.com")
    @Size(max = 150, message = "O e-mail deve ter no {max} caracteres.")
    @Email(message = "O e-mail é inválido.")
    private String email;

    @Schema(name = "phone", minLength = 9, maxLength = 15, pattern = "^\\+?\\d{9,14}$",
            description = "Número de telefone do usuário", example = "+5547999999999")
    @ValidPhoneNumber
    private String phone;

    @Schema(name = "birthDate", description = "Data de nascimento do usuário", example = "1990-01-01")
    private LocalDate birthDate;

}
