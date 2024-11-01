package br.com.juhmaran.petshop_api.api.common.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class Product {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String category;
    private String supplier;
    private LocalDate expirationDate;
    private String barcode;
    private Double weight;
    private Dimensions dimensions;
    private Set<Purchase> purchase;
    private String imageUrl;

}
