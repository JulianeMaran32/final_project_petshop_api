package br.com.juhmaran.petshop_api.api.products.services;

import br.com.juhmaran.petshop_api.api.products.dtos.ProductRequest;
import br.com.juhmaran.petshop_api.api.products.dtos.ProductResponse;
import br.com.juhmaran.petshop_api.api.products.dtos.ProductUpdateRequest;

import java.util.List;

public interface ProductService {

    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    ProductResponse createProduct(ProductRequest productEntity);

    ProductResponse updateProduct(Long id, ProductUpdateRequest updateRequest);

    String deleteProduct(Long id);

    List<ProductResponse> searchProducts(String name, String category, String supplier, String barcode);

}
