package br.com.juhmaran.petshop_api.api.products.entities;

import br.com.juhmaran.petshop_api.api.products.dimensions.entities.DimensionsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100, columnDefinition = "VARCHAR(100) NOT NULL")
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR(255)")
    private String description;

    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal price;

    @Column(name = "quantity", nullable = false, columnDefinition = "INT UNSIGNED NOT NULL")
    private Integer quantity;

    @Column(name = "category", length = 100, columnDefinition = "VARCHAR(100)")
    private String category;

    @Column(name = "supplier", length = 100, columnDefinition = "VARCHAR(100)")
    private String supplier;

    @Column(name = "expiration_date", nullable = false, updatable = false, columnDefinition = "DATE NOT NULL")
    private LocalDate expirationDate;

    /**
     * Código de barras do produto
     */
    @Column(name = "barcode", length = 13, columnDefinition = "VARCHAR(13)")
    private String barcode;

    @Column(name = "weight", columnDefinition = "DECIMAL(10,2)")
    private Double weight;

    @Embedded
    private DimensionsEntity dimensions;

//    @ManyToMany(mappedBy = "products")
//    private Set<PurchaseEntity> purchases = new HashSet<>();

}
