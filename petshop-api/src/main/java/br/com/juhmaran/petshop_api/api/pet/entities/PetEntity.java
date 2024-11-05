package br.com.juhmaran.petshop_api.api.pet.entities;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.api.common.enums.Species;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.core.auditable.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pets")
public class PetEntity extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    private Species species;

    @Column(length = 50)
    private String breed;

    @Column(name = "birth_date", columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(length = 25)
    private String color;

    @Column(length = 25)
    private String size;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Boolean castrated;

    @Column(columnDefinition = "DECIMAL(5,2)")
    private Double weight;

    @Column(length = 250)
    private String healthHistory;

    @Transient
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_pet_user"))
    private UserEntity user;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDate;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "updated_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedDate;

}
