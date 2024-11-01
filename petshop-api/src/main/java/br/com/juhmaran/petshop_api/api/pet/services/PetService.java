package br.com.juhmaran.petshop_api.api.pet.services;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.api.common.enums.Species;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetRequest;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetResponse;

import java.util.List;

public interface PetService {

    List<PetResponse> getPets(String name, Boolean castrated, Species species, Gender gender);

    PetResponse createPetForUser(Long userId, PetRequest petRequest);

    PetResponse createPet(PetRequest petRequest, String username);

    PetResponse getPetById(Long id);

    PetResponse updatePetById(Long id, PetRequest petRequest);

    PetResponse patchPetById(Long id, PetRequest petRequest);

    void deletePetById(Long id);

    List<PetResponse> getUserPets(String name);

}
