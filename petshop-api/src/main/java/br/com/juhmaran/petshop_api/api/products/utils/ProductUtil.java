package br.com.juhmaran.petshop_api.api.products.utils;

import br.com.juhmaran.petshop_api.api.products.entities.ProductEntity;
import br.com.juhmaran.petshop_api.api.products.repositories.ProductRepository;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopNotFoundException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class ProductUtil {

    ProductRepository productRepository;

    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new PetShopNotFoundException("Produto não encontrado."));
    }

}
