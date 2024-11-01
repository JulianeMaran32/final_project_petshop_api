package br.com.juhmaran.petshop_api.core.security.role.repositories;

import br.com.juhmaran.petshop_api.core.security.role.entities.RoleEntity;
import br.com.juhmaran.petshop_api.api.common.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Juliane Maran
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(RoleType name);

}
