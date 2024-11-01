package br.com.juhmaran.petshop_api.api.agenda.dto;

import br.com.juhmaran.petshop_api.api.address.dtos.AddressRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(name = "OwnerData", description = "Dados do proprietário.")
@Data
public class OwnerData {

    @Schema(name = "fullName", minLength = 3, maxLength = 150, requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nome do usuário.", example = "Juliane Maran")
    @Size(min = 3, max = 150, message = "O nome deve ter entre {min} e {max} caracteres.")
    @NotBlank(message = "O nome é obrigatório.")
    private String fullName;

    @Schema(name = "contactPhone", minLength = 9, maxLength = 15, pattern = "^\\+?\\d{9,14}$",
            description = "Número de telefone do usuário", example = "+5547999999999")
    @Pattern(regexp = "^\\+?\\d{9,14}$", message = "O telefone é inválido.")
    private String contactPhone;

    @Schema(name = "email", format = "email", maxLength = 150, requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Email do usuário.", example = "usuario@email.com")
    @Size(max = 150, message = "O e-mail deve ter no {max} caracteres.")
    @Email(message = "O e-mail é inválido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;

    @Schema(name = "address", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Endereço do usuário.")
    private AddressRequest address;

}
