package br.com.juhmaran.petshop_api.core.security.role.entities;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.api.common.enums.Species;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pet_view")
public class PetView {

    @Id
    private Long id;
    private String name;
    private Species species;
    private Gender gender;
    private Boolean castrated;

}
