package br.com.juhmaran.petshop_api.api.pet.services;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.api.common.enums.Species;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetRequest;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PetService {

    List<PetResponse> filterPets(Long id, String name, String breed, Species species,
                                 Gender gender, LocalDateTime createdDate);


    PetResponse createPet(PetRequest petRequest, String username);

    PetResponse createPetForCustomer(PetRequest petRequest);

    PetResponse updatePet(Long petId, PetRequest petRequest, String username);

    void deletePet(Long petId, String username);

    PetResponse updatePetForCustomer(Long petId, PetRequest petRequest);

    void deletePetForCustomer(Long petId);
    
}
