package br.com.juhmaran.petshop_api.api.address.mapping;

import br.com.juhmaran.petshop_api.api.address.dtos.AddressRequest;
import br.com.juhmaran.petshop_api.api.address.dtos.AddressResponse;
import br.com.juhmaran.petshop_api.api.address.dtos.ViaCepResponse;
import br.com.juhmaran.petshop_api.api.address.entities.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    AddressEntity requestToAddress(AddressRequest addressRequestDTO);

    AddressResponse addressToAddressResponse(AddressEntity address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "zipCode", source = "cep")
    @Mapping(target = "street", source = "logradouro")
    @Mapping(target = "city", source = "localidade")
    @Mapping(target = "state", source = "uf")
    @Mapping(target = "country", constant = "Brasil")
    AddressResponse viaCepToAddressResponse(ViaCepResponse viaCepResponse);

    void updateAddressFromRequest(AddressRequest addressRequest, @MappingTarget AddressEntity address);

}
