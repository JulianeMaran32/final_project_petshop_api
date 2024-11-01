package br.com.juhmaran.petshop_api.api.pet.repositories;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.api.common.enums.Species;
import br.com.juhmaran.petshop_api.api.pet.entities.PetEntity;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.core.security.role.entities.PetView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<PetEntity, Long> {

    List<PetEntity> findByUser(UserEntity user);

    Optional<PetEntity> findByIdAndUser(Long id, UserEntity user);

    List<PetEntity> findByUserId(Long userId);

    @Query("SELECT p FROM PetView p WHERE (:name IS NULL OR p.name = :name) " +
            "AND (:castrated IS NULL OR p.castrated = :castrated) " +
            "AND (:species IS NULL OR p.species = :species) " +
            "AND (:gender IS NULL OR p.gender = :gender)")
    List<PetView> findPetsByView(@Param("name") String name,
                                 @Param("castrated") Boolean castrated,
                                 @Param("species") Species species,
                                 @Param("gender") Gender gender);


}
