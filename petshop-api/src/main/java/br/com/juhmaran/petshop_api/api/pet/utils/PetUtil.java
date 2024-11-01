package br.com.juhmaran.petshop_api.api.pet.utils;

import br.com.juhmaran.petshop_api.api.pet.entities.PetEntity;
import br.com.juhmaran.petshop_api.api.pet.repositories.PetRepository;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.api.user.repositories.UserRepository;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopNotFoundException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class PetUtil {

    UserRepository userRepository;
    PetRepository petRepository;

    public PetEntity findPetById(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new PetShopNotFoundException("Pet com o id não encontrado: " + petId));
    }

    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new PetShopNotFoundException("Usuário com o id não encontrado: " + userId));
    }

    public UserEntity findUserByEmail(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new PetShopNotFoundException("Usuário com o email não encontrado: " + username));
    }

}
