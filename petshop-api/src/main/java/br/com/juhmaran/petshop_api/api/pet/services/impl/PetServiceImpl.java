package br.com.juhmaran.petshop_api.api.pet.services.impl;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.api.common.enums.RoleType;
import br.com.juhmaran.petshop_api.api.common.enums.Species;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetRequest;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetResponse;
import br.com.juhmaran.petshop_api.api.pet.entities.PetEntity;
import br.com.juhmaran.petshop_api.api.pet.mapping.PetMapper;
import br.com.juhmaran.petshop_api.api.pet.repositories.PetRepository;
import br.com.juhmaran.petshop_api.api.pet.services.PetService;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.api.user.repositories.UserRepository;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Transactional(readOnly = true)
    @Override
    public List<PetResponse> filterPets(Long id, String name, String breed, Species species,
                                        Gender gender, LocalDateTime createdDate) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = findUserByEmail(username);
        List<PetEntity> petEntities;

        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleType.CUSTOMER))) {
            petEntities = petRepository.filterPetsByUser(user.getId(), id, name, breed, species, gender, createdDate);
        } else {
            petEntities = petRepository.filterPets(id, name, breed, species, gender, createdDate);
        }

        return PetMapper.INSTANCE.toPetResponseList(petEntities);
    }

    @Transactional
    @Override
    public PetResponse createPet(PetRequest petRequest, String username) {
        UserEntity user = findUserByEmail(username);
        PetEntity pet = PetMapper.INSTANCE.toEntity(petRequest);
        pet.setUser(user);
        PetEntity savedPet = petRepository.save(pet);
        return PetMapper.INSTANCE.toResponse(savedPet);
    }

    @Transactional
    @Override
    public PetResponse createPetForCustomer(PetRequest petRequest) {
        UserEntity user = getUserById(petRequest.getUserId());
        validateUserIsCustomer(user);
        PetEntity pet = PetMapper.INSTANCE.toEntity(petRequest);
        pet.setUser(user);
        PetEntity savedPet = petRepository.save(pet);
        return PetMapper.INSTANCE.toResponse(savedPet);
    }

    @Transactional
    @Override
    public PetResponse updatePet(Long petId, PetRequest petRequest, String username) {
        UserEntity user = findUserByEmail(username);
        PetEntity pet = getPetEntity(petId, user);
        PetMapper.INSTANCE.updateEntityFromRequest(petRequest, pet);
        PetEntity updatedPet = petRepository.save(pet);
        return PetMapper.INSTANCE.toResponse(updatedPet);
    }

    @Transactional
    @Override
    public void deletePet(Long petId, String username) {
        UserEntity user = findUserByEmail(username);
        PetEntity pet = getPetEntity(petId, user);
        petRepository.delete(pet);
    }

    @Transactional
    @Override
    public PetResponse updatePetForCustomer(Long petId, PetRequest petRequest) {
        PetEntity pet = getPetEntity(petId);
        PetMapper.INSTANCE.updateEntityFromRequest(petRequest, pet);
        PetEntity updatedPet = petRepository.save(pet);
        return PetMapper.INSTANCE.toResponse(updatedPet);
    }

    @Transactional
    @Override
    public void deletePetForCustomer(Long petId) {
        PetEntity pet = getPetEntity(petId);
        petRepository.delete(pet);
    }

    private void validateUserIsCustomer(UserEntity user) {
        boolean isCustomer = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(RoleType.CUSTOMER));
        if (!isCustomer) {
            throw new RuntimeException("O usuário especificado não é um CUSTOMER");
        }
    }

    private UserEntity findUserByEmail(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new PetShopNotFoundException("Email não encontrado."));
    }

    private UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new PetShopNotFoundException("Usuário não encontrado."));
    }


    private PetEntity getPetEntity(Long petId, UserEntity user) {
        return petRepository.findByIdAndUserId(petId, user.getId())
                .orElseThrow(() -> new PetShopNotFoundException("Pet não encontrado ou não pertence ao usuário."));
    }

    private PetEntity getPetEntity(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new PetShopNotFoundException("Pet não encontrado."));
    }

}
