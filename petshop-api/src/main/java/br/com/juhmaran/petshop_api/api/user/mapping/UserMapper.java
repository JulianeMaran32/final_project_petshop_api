package br.com.juhmaran.petshop_api.api.user.mapping;

import br.com.juhmaran.petshop_api.api.user.dto.response.UserResponse;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toUserResponse(UserEntity users);

}
