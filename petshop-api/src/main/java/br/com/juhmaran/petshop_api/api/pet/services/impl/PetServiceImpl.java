package br.com.juhmaran.petshop_api.api.pet.services.impl;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.api.common.enums.Species;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetRequest;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetResponse;
import br.com.juhmaran.petshop_api.api.pet.entities.PetEntity;
import br.com.juhmaran.petshop_api.api.pet.mapping.PetMapper;
import br.com.juhmaran.petshop_api.api.pet.repositories.PetRepository;
import br.com.juhmaran.petshop_api.api.pet.services.PetService;
import br.com.juhmaran.petshop_api.api.pet.utils.PetUtil;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Override
    public List<PetResponse> getPets(String name, Boolean castrated, Species species,
                                     Gender gender) {
        return petRepository.findPetsByView(name, castrated, species, gender).stream()
                .map(pet -> new PetResponse(pet.getId(), pet.getName(), pet.getSpecies(),
                        pet.getGender(), pet.getCastrated()))
                .toList();
    }

    @Override
    public PetResponse createPetForUser(Long userId, PetRequest petRequest) {
        UserEntity user = PetUtil.findUserById(userId);
        PetEntity petEntity = PetMapper.INSTANCE.toEntity(petRequest);
        petEntity.setUser(user);
        PetEntity savedPet = petRepository.save(petEntity);
        return PetMapper.INSTANCE.toResponse(savedPet);
    }

    @Override
    public PetResponse createPet(PetRequest petRequest, String username) {
        UserEntity user = PetUtil.findUserByEmail(username);
        PetEntity petEntity = PetMapper.INSTANCE.toEntity(petRequest);
        petEntity.setUser(user);
        PetEntity savedPet = petRepository.save(petEntity);
        return PetMapper.INSTANCE.toResponse(savedPet);
    }

//    @Override
//    public PetResponse createPetForAuthenticatedUser(String username, PetRequest petRequest) {
//        UserEntity user = PetUtil.findUserByEmail(username);
//        PetEntity petEntity = PetMapper.INSTANCE.toEntity(petRequest);
//        petEntity.setUser(user);
//        PetEntity savedPet = petRepository.save(petEntity);
//        return PetMapper.INSTANCE.toResponse(savedPet);
//    }

    @Override
    public PetResponse getPetById(Long id) {
        PetEntity petEntity = PetUtil.findPetById(id);
        return PetMapper.INSTANCE.toResponse(petEntity);
    }

    @Override
    public PetResponse updatePetById(Long id, PetRequest petRequest) {
        PetEntity existingPet = PetUtil.findPetById(id);
        PetEntity updatedPet = PetMapper.INSTANCE.toEntity(petRequest);
        updatedPet.setId(existingPet.getId());
        updatedPet.setUser(existingPet.getUser());
        PetEntity savedPet = petRepository.save(updatedPet);
        return PetMapper.INSTANCE.toResponse(savedPet);
    }

    @Override
    public PetResponse patchPetById(Long id, PetRequest petRequest) {
        PetEntity existingPet = PetUtil.findPetById(id);
        if (petRequest.getName() != null) existingPet.setName(petRequest.getName());
        if (petRequest.getSpecies() != null) existingPet.setSpecies(petRequest.getSpecies());
        if (petRequest.getBreed() != null) existingPet.setBreed(petRequest.getBreed());
        if (petRequest.getBirthDate() != null) existingPet.setBirthDate(petRequest.getBirthDate());
        if (petRequest.getColor() != null) existingPet.setColor(petRequest.getColor());
        if (petRequest.getSize() != null) existingPet.setSize(petRequest.getSize());
        if (petRequest.getGender() != null) existingPet.setGender(petRequest.getGender());
        if (petRequest.getCastrated() != null) existingPet.setCastrated(petRequest.getCastrated());
        if (petRequest.getWeight() != 0) existingPet.setWeight(petRequest.getWeight());
        if (petRequest.getHealthHistory() != null) existingPet.setHealthHistory(petRequest.getHealthHistory());
        PetEntity savedPet = petRepository.save(existingPet);
        return PetMapper.INSTANCE.toResponse(savedPet);
    }

    @Override
    public void deletePetById(Long id) {
        PetEntity petEntity = PetUtil.findPetById(id);
        petRepository.delete(petEntity);
    }

    @Override
    public List<PetResponse> getUserPets(String username) {
        UserEntity user = PetUtil.findUserByEmail(username);
        return petRepository.findByUser(user).stream()
                .map(PetMapper.INSTANCE::toResponse)
                .toList();
    }

    public boolean isPetOwner(Authentication authentication, Long petId) {
        String username = authentication.getName();
        PetEntity pet = PetUtil.findPetById(petId);
        if (!pet.getUser().getEmail().equals(username)) {
            throw new AccessDeniedException("User does not have permission to access this pet");
        }
        return true;
    }

}
