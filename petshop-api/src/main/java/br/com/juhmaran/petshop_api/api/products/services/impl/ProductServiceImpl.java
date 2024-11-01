package br.com.juhmaran.petshop_api.api.products.services.impl;

import br.com.juhmaran.petshop_api.api.products.dtos.ProductRequest;
import br.com.juhmaran.petshop_api.api.products.dtos.ProductResponse;
import br.com.juhmaran.petshop_api.api.products.dtos.ProductUpdateRequest;
import br.com.juhmaran.petshop_api.api.products.entities.ProductEntity;
import br.com.juhmaran.petshop_api.api.products.mapping.ProductMapper;
import br.com.juhmaran.petshop_api.api.products.repositories.ProductRepository;
import br.com.juhmaran.petshop_api.api.products.services.ProductService;
import br.com.juhmaran.petshop_api.api.products.utils.ProductUtil;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> findAll() {
        return ProductMapper.INSTANCE.toProductResponseList(productRepository.findAll());
    }

    @Override
    public ProductResponse findById(Long id) {
        ProductEntity productEntity = ProductUtil.getProductById(id);
        return ProductMapper.INSTANCE.toProductResponse(productEntity);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        if (productRepository.existsByNameAndCategory(productRequest.getName(), productRequest.getCategory())) {
            throw new PetShopConflictException("Produto com o mesmo nome e categoria já existe.");
        }
        ProductEntity productEntity = ProductMapper.INSTANCE.toEntity(productRequest);
        productRepository.save(productEntity);
        return ProductMapper.INSTANCE.toProductResponse(productEntity);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateRequest updateRequest) {
        productRepository.updateProduct(updateRequest.getName(),
                updateRequest.getDescription(),
                updateRequest.getPrice(),
                updateRequest.getQuantityInStock(),
                updateRequest.getCategory(),
                updateRequest.getSupplier(),
                updateRequest.getExpirationDate(),
                updateRequest.getBarcode(),
                updateRequest.getWeight(),
                updateRequest.getDimensions().getHeight(),
                updateRequest.getDimensions().getWidth(),
                updateRequest.getDimensions().getDepth());

        ProductEntity updatedProduct = ProductUtil.getProductById(id);
        return ProductMapper.INSTANCE.toProductResponse(updatedProduct);
    }

    @Override
    public String deleteProduct(Long id) {
        ProductEntity productEntity = ProductUtil.getProductById(id);
        productRepository.delete(productEntity);
        return "Produto deletado com sucesso.";
    }

    @Override
    public List<ProductResponse> searchProducts(String name, String category, String supplier, String barcode) {
        List<ProductEntity> products = productRepository.searchProducts(name, category, supplier, barcode);
        return ProductMapper.INSTANCE.toProductResponseList(products);
    }

}
