package br.com.juhmaran.petshop_api.core.initializer.admins;

import br.com.juhmaran.petshop_api.api.common.enums.RoleType;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.api.user.repositories.UserRepository;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.*;
import br.com.juhmaran.petshop_api.core.security.role.entities.RoleEntity;
import br.com.juhmaran.petshop_api.core.security.role.repositories.RoleRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static br.com.juhmaran.petshop_api.core.initializer.constants.InitializerConstants.*;

/**
 * @author Juliane Maran
 */
@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class SuperAdminInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
        log.info("Iniciando a verificação e criação do Super Admin.");
        try {
            ifNotExistCreateAdmin();
        } catch (PetShopBadRequestException | PetShopUnauthorizedException | PetShopForbiddenException |
                 PetShopNotFoundException | PetShopInternalServerErrorException ex) {
            log.error("Erro ao inicializar Super Admin: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inicializar Super Admin: {}", ex.getMessage());
            throw new PetShopInternalServerErrorException("Erro inesperado ao inicializar Super Admin", ex);
        }
        log.info("Verificação e criação do Super Admin concluída.");
    }

    private void ifNotExistCreateAdmin() {
        log.info("Verificando se a role SUPER_ADMIN existe.");
        Optional<RoleEntity> optionalRole = roleRepository.findByName(RoleType.SUPER_ADMIN);
        if (optionalRole.isEmpty()) {
            log.warn("Role SUPER_ADMIN não encontrada. A criação do Super Admin será abortada.");
            throw new PetShopNotFoundException("Role SUPER_ADMIN não encontrada");
        }
        log.info("Role SUPER_ADMIN encontrada. Verificando se o Super Admin já existe.");
        if (userRepository.findByCpf(SUPER_ADMIN_CPF).isEmpty() &&
                userRepository.findByEmail(SUPER_ADMIN_EMAIL).isEmpty()) {
            log.info("Super Admin não encontrado. Criando novo Super Admin.");
            criarSuperAdmin(optionalRole.get());
        } else {
            log.info("Super Admin já existe. Nenhuma ação necessária.");
        }
    }

    private void criarSuperAdmin(RoleEntity superAdminRole) {
        String encryptedPassword = passwordEncoder.encode(SUPER_ADMIN_PASSWORD);
        var superAdminUser = new UserEntity();
        superAdminUser.setName(SUPER_ADMIN_NAME);
        superAdminUser.setCpf(SUPER_ADMIN_CPF);
        superAdminUser.setEmail(SUPER_ADMIN_EMAIL);
        superAdminUser.setPhone(SUPER_ADMIN_PHONE);
        superAdminUser.setPassword(encryptedPassword);
        superAdminUser.setEnabled(true);
        superAdminUser.setRoles(new HashSet<>(Collections.singletonList(superAdminRole)));
        superAdminUser.setCreatedBy("system"); // Set the created_by field
        superAdminUser.setCreatedByRole("ADMIN"); // Set the created_by_role field
        userRepository.save(superAdminUser);
        log.info("Super Admin criado com sucesso: {}", superAdminUser);
    }
}
