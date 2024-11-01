package br.com.juhmaran.petshop_api.core.security.role.mapping;

import br.com.juhmaran.petshop_api.core.security.role.dtos.RoleResponse;
import br.com.juhmaran.petshop_api.core.security.role.entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Juliane Maran
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleResponse toRoleResponse(RoleEntity users);

}
