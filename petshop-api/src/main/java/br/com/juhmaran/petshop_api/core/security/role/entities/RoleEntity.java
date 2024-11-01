package br.com.juhmaran.petshop_api.core.security.role.entities;

import br.com.juhmaran.petshop_api.api.common.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Juliane Maran
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false,
            columnDefinition = "ENUM('SUPER_ADMIN', 'ADMIN', 'USER', 'OWNER', 'CUSTOMER', 'EMPLOYEE', 'VETERINARIAN') NOT NULL")
    @Enumerated(value = EnumType.STRING)
    private RoleType name;

    @Column(name = "description", nullable = false, length = 150, columnDefinition = "VARCHAR(150) NOT NULL")
    private String description;

}