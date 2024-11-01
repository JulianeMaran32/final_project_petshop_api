package br.com.juhmaran.petshop_api.api.products.repositories;

import br.com.juhmaran.petshop_api.api.products.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    boolean existsByNameAndCategory(String name, String category);

    @Procedure(name = "searchProducts")
    List<ProductEntity> searchProducts(@Param("p_name") String name,
                                       @Param("p_category") String category,
                                       @Param("p_supplier") String supplier,
                                       @Param("p_barcode") String barcode);

    @Procedure(name = "updateProduct")
    void updateProduct(@Param("p_name") String name,
                       @Param("p_description") String description,
                       @Param("p_price") BigDecimal price,
                       @Param("p_quantity_in_stock") Integer quantityInStock,
                       @Param("p_category") String category,
                       @Param("p_supplier") String supplier,
                       @Param("p_expiration_date") LocalDate expirationDate,
                       @Param("p_barcode") String barcode,
                       @Param("p_weight") Double weight,
                       @Param("p_height") Double height,
                       @Param("p_width") Double width,
                       @Param("p_depth") Double depth);

}
