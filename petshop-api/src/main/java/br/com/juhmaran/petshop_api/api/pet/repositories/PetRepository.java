package br.com.juhmaran.petshop_api.api.pet.repositories;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.api.common.enums.Species;
import br.com.juhmaran.petshop_api.api.pet.entities.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<PetEntity, Long> {

    Optional<PetEntity> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT p FROM PetEntity p WHERE " +
            "(:id IS NULL OR p.id = :id) AND " +
            "(:name IS NULL OR p.name LIKE CONCAT('%', :name, '%')) AND " +
            "(:breed IS NULL OR p.breed LIKE CONCAT('%', :breed, '%')) AND " +
            "(:species IS NULL OR p.species = :species) AND " +
            "(:gender IS NULL OR p.gender = :gender) AND " +
            "(:createdDate IS NULL OR p.createdDate = :createdDate)")
    List<PetEntity> filterPets(@Param("id") Long id,
                               @Param("name") String name,
                               @Param("breed") String breed,
                               @Param("species") Species species,
                               @Param("gender") Gender gender,
                               @Param("createdDate") LocalDateTime createdDate);

    @Query("SELECT p FROM PetEntity p WHERE p.user.id = :userId AND " +
            "(:id IS NULL OR p.id = :id) AND " +
            "(:name IS NULL OR p.name LIKE CONCAT('%', :name, '%')) AND " +
            "(:breed IS NULL OR p.breed LIKE CONCAT('%', :breed, '%')) AND " +
            "(:species IS NULL OR p.species = :species) AND " +
            "(:gender IS NULL OR p.gender = :gender) AND " +
            "(:createdDate IS NULL OR p.createdDate = :createdDate)")
    List<PetEntity> filterPetsByUser(@Param("userId") Long userId,
                                     @Param("id") Long id,
                                     @Param("name") String name,
                                     @Param("breed") String breed,
                                     @Param("species") Species species,
                                     @Param("gender") Gender gender,
                                     @Param("createdDate") LocalDateTime createdDate);
}
