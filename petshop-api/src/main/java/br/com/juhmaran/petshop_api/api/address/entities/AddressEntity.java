package br.com.juhmaran.petshop_api.api.address.entities;

import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zip_code", nullable = false, length = 10, columnDefinition = "VARCHAR(10) NOT NULL")
    private String zipCode;

    @Column(name = "street", nullable = false, length = 150, columnDefinition = "VARCHAR(150) NOT NULL")
    private String street;

    @Column(name = "complement", length = 100, columnDefinition = "VARCHAR(100)")
    private String complement;

    @Column(name = "unit", length = 50, columnDefinition = "VARCHAR(50)")
    private String unit;

    @Column(name = "neighborhood", length = 150, columnDefinition = "VARCHAR(150) NOT NULL")
    private String neighborhood;

    @Column(name = "city", nullable = false, length = 100, columnDefinition = "VARCHAR(100) NOT NULL")
    private String city;

    @Column(name = "state", nullable = false, length = 50, columnDefinition = "VARCHAR(50) NOT NULL")
    private String state;

    @Column(name = "country", nullable = false, length = 50, columnDefinition = "VARCHAR(50) NOT NULL")
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT UNSIGNED", foreignKey = @ForeignKey(name = "FK_user_address"))
    private UserEntity user;

}
