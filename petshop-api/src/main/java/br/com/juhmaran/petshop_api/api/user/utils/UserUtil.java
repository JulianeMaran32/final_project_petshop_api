package br.com.juhmaran.petshop_api.api.user.utils;

import br.com.juhmaran.petshop_api.api.common.enums.RoleType;
import br.com.juhmaran.petshop_api.api.user.dto.request.UserRegistrationRequest;
import br.com.juhmaran.petshop_api.api.user.dto.request.UserUpdateRequest;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.api.user.repositories.UserRepository;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.*;
import br.com.juhmaran.petshop_api.core.security.role.entities.RoleEntity;
import br.com.juhmaran.petshop_api.core.security.role.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUtil {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public void verifyCpf(UserRegistrationRequest registrationDto) {
        if (userRepository.existsByCpf(registrationDto.getCpf())) {
            log.warn("Tentativa de registro com CPF já cadastrado: {}", registrationDto.getCpf());
            throw new PetShopConflictException("CPF já cadastrado");
        }
    }

    public void verifyEmail(UserRegistrationRequest registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            log.warn("Tentativa de registro com e-mail já cadastrado: {}", registrationDto.getEmail());
            throw new PetShopConflictException("E-mail já cadastrado");
        }
    }

    public static void checkPassword(UserRegistrationRequest registrationDto) {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            log.warn("Tentativa de registro com senhas não coincidentes para o e-mail: {}", registrationDto.getEmail());
            throw new PetShopConflictException("As senhas não coincidem");
        }
    }

    public static UserEntity mapToUserEntity(UserRegistrationRequest registrationDto, String encodedPassword) {
        var user = new UserEntity();
        user.setName(registrationDto.getName());
        user.setCpf(registrationDto.getCpf());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(encodedPassword);
        user.setPhone(registrationDto.getPhone());
        user.setEnabled(true);
        return user;
    }

    public void convertStringRoleToRoleType(UserRegistrationRequest registrationDto, UserEntity user) {
        RoleType roleType;
        try {
            roleType = RoleType.valueOf(registrationDto.getRole());
        } catch (PetShopBadRequestException e) {
            throw new PetShopBadRequestException("Role inválida: " + registrationDto.getRole());
        }

        RoleEntity role = roleRepository.findByName(roleType)
                .orElseThrow(() -> new PetShopNotFoundException("Role não encontrada: " + registrationDto.getRole()));
        user.getRoles().add(role);
    }

    public void userUpdate(Long id, UserUpdateRequest userUpdateRequest, UserEntity userEntity) {
        if (userUpdateRequest.getEmail() != null) {
            if (userRepository.existsByEmailAndIdNot(userUpdateRequest.getEmail(), id)) {
                throw new PetShopConflictException("Email já está cadastrado por outro usuário.");
            }
            userEntity.setEmail(userUpdateRequest.getEmail());
        }

        if (userUpdateRequest.getCpf() != null) {
            if (userRepository.existsByCpfAndIdNot(userUpdateRequest.getCpf(), id)) {
                throw new PetShopConflictException("CPF já está cadastrado por outro usuário.");
            }
            userEntity.setCpf(userUpdateRequest.getCpf());
        }

        if (userUpdateRequest.getName() != null) {
            userEntity.setName(userUpdateRequest.getName());
        }

        if (userUpdateRequest.getPhone() != null) {
            userEntity.setPhone(userUpdateRequest.getPhone());
        }

        if (userUpdateRequest.getBirthDate() != null) {
            userEntity.setBirthDate(userUpdateRequest.getBirthDate());
        }

    }

    public UserEntity getUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new PetShopNotFoundException("Usuário não encontrado"));
    }

    public UserEntity getUserEntity(Authentication authentication) {
        String authenticatedUserEmail = authentication.getName();
        return userRepository.findByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new PetShopNotFoundException("Usuário autenticado não encontrado."));
    }

    public static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new PetShopUnauthorizedException("Usuário não autenticado.");
        }
        return authentication;
    }

    public static void userNotPermission(UserEntity authenticatedUser, Long id, String message) {
        if (!authenticatedUser.getId().equals(id)) {
            throw new PetShopForbiddenException(message);
        }
    }

    public static void checkUserRole(UserEntity user, List<String> allowedRoles) {
        boolean hasRole = user.getRoles().stream()
                .anyMatch(role -> allowedRoles.contains(role.getName()));
        if (!hasRole) {
            throw new PetShopForbiddenException("Usuário não possui permissão.");
        }
    }

    public boolean isEmailAlreadyRegistered(String email) {
        return userRepository.existsByEmail(email);
    }

}