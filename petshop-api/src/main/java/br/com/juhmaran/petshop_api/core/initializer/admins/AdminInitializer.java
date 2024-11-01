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
@Order(3)
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
        log.info("Evento de refresh do contexto da aplicação recebido.");
        try {
            ifNotExistCreateAdmin();
        } catch (PetShopBadRequestException | PetShopUnauthorizedException | PetShopForbiddenException |
                 PetShopNotFoundException | PetShopInternalServerErrorException ex) {
            log.error("Erro ao inicializar Admin: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inicializar Admin: {}", ex.getMessage());
            throw new PetShopInternalServerErrorException("Erro inesperado ao inicializar Admin", ex);
        }
        log.info("Processo de inicialização do administrador concluído.");
    }

    private void ifNotExistCreateAdmin() {
        log.info("Verificando se a role ADMIN existe.");
        Optional<RoleEntity> optionalRole = roleRepository.findByName(RoleType.ADMIN);
        if (optionalRole.isEmpty()) {
            log.error("Role ADMIN não encontrada.");
            throw new PetShopNotFoundException("Role ADMIN não encontrada");
        }
        log.info("Role ADMIN encontrada. Verificando se o usuário administrador já existe.");
        if (userRepository.findByCpf(ADMIN_CPF).isEmpty() && userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
            log.info("Usuário administrador não encontrado. Criando novo usuário administrador.");
            createAdminUser(optionalRole.get());
        } else {
            log.info("Usuário administrador já existe. Nenhuma ação necessária.");
        }
    }

    private void createAdminUser(RoleEntity adminRole) {
        String encryptedPassword = passwordEncoder.encode(ADMIN_PASSWORD);
        var adminUser = new UserEntity();
        adminUser.setName(ADMIN_NAME);
        adminUser.setCpf(ADMIN_CPF);
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setPhone(ADMIN_PHONE);
        adminUser.setPassword(encryptedPassword);
        adminUser.setEnabled(true);
        adminUser.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
        adminUser.setCreatedBy("system"); // Set the created_by field
        adminUser.setCreatedByRole("ADMIN"); // Set the created_by_role field
        userRepository.save(adminUser);
        log.info("Usuário administrador criado com sucesso.");
    }

}