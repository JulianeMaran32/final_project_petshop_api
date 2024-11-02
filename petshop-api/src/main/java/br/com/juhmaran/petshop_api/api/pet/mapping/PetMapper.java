package br.com.juhmaran.petshop_api.api.pet.mapping;

import br.com.juhmaran.petshop_api.api.pet.dtos.PetRequest;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetResponse;
import br.com.juhmaran.petshop_api.api.pet.entities.PetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetMapper {

    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    PetEntity toEntity(PetRequest petRequest);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "age",
            expression = "java(br.com.juhmaran.petshop_api.api.pet.utils.PetAgeCalculator.calculateAge(petEntity.getBirthDate()))")
    PetResponse toResponse(PetEntity petEntity);

    List<PetResponse> toPetResponseList(List<PetEntity> petEntity);

    void updateEntityFromRequest(PetRequest petRequest, @MappingTarget PetEntity pet);
}
