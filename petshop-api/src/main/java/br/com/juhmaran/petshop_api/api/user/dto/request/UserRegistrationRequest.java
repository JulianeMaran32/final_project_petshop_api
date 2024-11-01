package br.com.juhmaran.petshop_api.api.user.dto.request;

import br.com.juhmaran.petshop_api.core.validator.annotations.ValidPassword;
import br.com.juhmaran.petshop_api.core.validator.annotations.ValidPhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@Schema(name = "UserRegistrationRequest", description = "Requisição de cadastro de usuário.")
public class UserRegistrationRequest {

    @Schema(name = "name", minLength = 3, maxLength = 150,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nome do usuário.", example = "Juliane Maran")
    @Size(min = 3, max = 150, message = "O nome deve ter entre {min} e {max} caracteres.")
    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @Schema(name = "cpf", pattern = "^\\d{11}$", minLength = 11, maxLength = 11,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Número do CPF do usuário", example = "12345678901")
    @CPF(message = "O CPF é inválido.")
    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    @Schema(name = "email", format = "email", maxLength = 150,
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Email do usuário.", example = "usuario@email.com")
    @Size(max = 150, message = "O e-mail deve ter no {max} caracteres.")
    @Email(message = "O e-mail é inválido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;

    @Schema(name = "password", format = "password", requiredMode = Schema.RequiredMode.REQUIRED,
            pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            description = "Senha deve conter no mínimo 8 caracteres, sendo pelo menos uma letra minúscula, uma letra " +
                    "maiúscula, um número e um caracter especial.", example = "Senh@Fort3")
    @NotBlank(message = "A senha é obrigatória.")
    @ValidPassword
    private String password;

    @Schema(name = "confirmPassword", format = "password", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Confirmar a senha", example = "Senh@Fort3")
    @NotBlank(message = "A confirmação de senha é obrigatória.")
    @ValidPassword
    private String confirmPassword;

    @Schema(name = "phone", minLength = 9, maxLength = 15, pattern = "^\\+?\\d{9,14}$",
            description = "Número de telefone do usuário", example = "+5547999999999")
    @NotBlank(message = "O telefone é obrigatório.")
    @ValidPhoneNumber
    private String phone;

    @Schema(name = "role", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Tipo de usuário. Pode ser ADMIN ou CUSTOMER.", example = "CUSTOMER")
    @NotBlank(message = "O tipo de usuário é obrigatório.")
    private String role;

    @Schema(name = "birthDate", format = "date", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Data de nascimento do usuário", example = "1990-01-01")
    private LocalDate birthDate;

}
