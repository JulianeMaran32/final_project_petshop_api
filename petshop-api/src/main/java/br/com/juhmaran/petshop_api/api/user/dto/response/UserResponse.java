package br.com.juhmaran.petshop_api.api.user.dto.response;

import br.com.juhmaran.petshop_api.core.security.role.dtos.RoleResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Schema(name = "UserResponse", description = "Resposta de usuário.")
public class UserResponse {

    @Schema(name = "id", description = "Identificador único do usuário", example = "123")
    private Long id;

    @Schema(name = "name", description = "Nome do usuário.", example = "Juliane Maran")
    private String name;

    @Schema(name = "cpf", description = "Número do CPF do usuário", example = "12345678901")
    private String cpf;

    @Schema(name = "email", description = "Email do usuário.", example = "usuario@email.com")
    private String email;

    @Schema(name = "phone", description = "Número de telefone do usuário", example = "+5547999999999")
    private String phone;

    @Schema(name = "enabled", description = "Informa se o usuário está ativo ou não", example = "true")
    private Boolean enabled;

    @Schema(name = "roles", description = "Lista os tipos de permissão do usuário",
            ref = "RoleResponse", example = "ROLE_USER")
    private Set<RoleResponse> roles;

    @Schema(name = "birthDate", description = "Data de nascimento do usuário", example = "1990-01-01")
    private LocalDate birthDate;

}
