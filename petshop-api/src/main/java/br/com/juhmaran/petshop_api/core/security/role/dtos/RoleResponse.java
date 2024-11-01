package br.com.juhmaran.petshop_api.core.security.role.dtos;

import br.com.juhmaran.petshop_api.api.common.enums.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author Juliane Maran
 */
@Data
@Builder
@Schema(title = "RoleResponse", description = "Classe que representa a resposta de uma role.")
public class RoleResponse {

    @Schema(description = "Identificador único da role.", example = "1")
    private Long id;

    @Schema(description = "Nome da role.", example = "ADMIN", implementation = RoleType.class)
    private RoleType name;

    @Schema(description = "Descrição da role.", example = "Administrador do sistema")
    private String description;

}