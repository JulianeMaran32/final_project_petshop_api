package br.com.juhmaran.petshop_api.api.common.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class Purchase {

    private Long id;
    private User user;
    private Set<Product> products;
    private LocalDateTime purchaseDate;

}
