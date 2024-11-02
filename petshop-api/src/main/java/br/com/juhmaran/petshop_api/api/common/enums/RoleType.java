package br.com.juhmaran.petshop_api.api.common.enums;

import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumeração que representa os diferentes tipos de roles no sistema.
 * <p>
 * Cada valor desta enumeração corresponde a um tipo específico de role que pode ser atribuída a um usuário.
 * </p>
 * <p>
 * As roles disponíveis são:
 * <ul>
 *     <li>{@link #SUPER_ADMIN} - Role de super administrador.</li>
 *     <li>{@link #ADMIN} - Role de administrador.</li>
 *     <li>{@link #USER} - Role de usuário comum.</li>
 *     <li>{@link #OWNER} - Role de proprietário.</li>
 *     <li>{@link #CUSTOMER} - Role de cliente.</li>
 *     <li>{@link #EMPLOYEE} - Role de funcionário.</li>
 *     <li>{@link #VETERINARIAN} - Role de veterinário.</li>
 * </ul>
 * </p>
 * <p>
 * Esta enumeração é usada para definir as permissões e acessos dos usuários no sistema.
 * </p>
 *
 * @author Juliane Maran
 */
@Schema(description = "Enumeração que representa os diferentes tipos de roles no sistema.",
        enumAsRef = true, example = "ADMIN",
        allowableValues = {"SUPER_ADMIN", "ADMIN", "USER", "OWNER", "CUSTOMER", "EMPLOYEE", "VETERINARIAN"})
public enum RoleType {

    SUPER_ADMIN,
    ADMIN,
    USER,
    OWNER,
    CUSTOMER,
    EMPLOYEE,
    VETERINARIAN;

    @JsonCreator
    public static RoleType fromString(String value) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.name().equalsIgnoreCase(value)) {
                return roleType;
            }
        }
        throw new PetShopInternalServerErrorException("RoleType inválido: " + value);
    }

}
