package br.com.juhmaran.petshop_api.api.products.mapping;

import br.com.juhmaran.petshop_api.api.products.dimensions.dtos.DimensionsRequest;
import br.com.juhmaran.petshop_api.api.products.dimensions.entities.DimensionsEntity;
import br.com.juhmaran.petshop_api.api.products.dtos.ProductRequest;
import br.com.juhmaran.petshop_api.api.products.dtos.ProductResponse;
import br.com.juhmaran.petshop_api.api.products.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // entidade para requet
    // response para entidade

    @Mapping(target = "id", ignore = true)
    ProductEntity toEntity(ProductRequest productRequest);

    ProductResponse toProductResponse(ProductEntity productEntity);

    List<ProductResponse> toProductResponseList(List<ProductEntity> productEntity);

    DimensionsEntity toDimensionsEntity(DimensionsRequest dimensions);

}
