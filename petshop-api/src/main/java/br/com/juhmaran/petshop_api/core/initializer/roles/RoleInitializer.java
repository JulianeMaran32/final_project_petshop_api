package br.com.juhmaran.petshop_api.core.initializer.roles;

import br.com.juhmaran.petshop_api.api.common.enums.RoleType;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.*;
import br.com.juhmaran.petshop_api.core.security.role.entities.RoleEntity;
import br.com.juhmaran.petshop_api.core.security.role.repositories.RoleRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * @author Juliane Maran
 */
@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class RoleInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
        log.info("Iniciando a inicialização das roles.");
        try {
            ifNotExistCreateRole();
        } catch (PetShopBadRequestException | PetShopUnauthorizedException | PetShopForbiddenException |
                 PetShopNotFoundException | PetShopInternalServerErrorException ex) {
            log.error("Erro ao inicializar roles: {}", ex.getMessage());
            throw ex;
        } catch (HttpMessageNotReadableException | HttpMessageNotWritableException ex) {
            log.error("Erro de solicitação ao inicializar roles: {}", ex.getMessage());
            throw new PetShopBadRequestException("Erro de solicitação ao inicializar roles", ex);
        } catch (Exception ex) {
            log.error("Erro inesperado ao inicializar roles: {}", ex.getMessage());
            throw new PetShopInternalServerErrorException("Erro inesperado ao inicializar roles", ex);
        }
        log.info("Inicialização das roles concluída.");
    }

    private void ifNotExistCreateRole() {
        log.info("Verificando e criando roles, se necessário.");
        RoleType[] roleNames = RoleType.values();
        Map<RoleType, String> roleDescriptionMap = Map.of(
                RoleType.SUPER_ADMIN, "Administrador do Sistema (Super Usuário)",
                RoleType.ADMIN, "Administrador do PetShop (Pessoa Jurídica)",
                RoleType.USER, "Usuário padrão do sistema",
                RoleType.OWNER, "Dono do PetShop (Pessoa Física)",
                RoleType.CUSTOMER, "Cliente do Petshop (Tutor do Pet)",
                RoleType.EMPLOYEE, "Funcionário do PetShop (Atendente, Tosador, etc)",
                RoleType.VETERINARIAN, "Médico Veterinário");
        Arrays.stream(roleNames).forEach(roleName -> {
            Optional<RoleEntity> optionalRole = roleRepository.findByName(roleName);
            optionalRole.ifPresentOrElse(
                    role -> log.info("Role '{}' já existe: {}", roleName, role),
                    () -> {
                        log.info("Role '{}' não encontrada. Criando nova role.", roleName);
                        criarRole(roleName, roleDescriptionMap.get(roleName));
                    }
            );
        });
        log.info("Verificação e criação de roles concluída.");
    }

    private void criarRole(RoleType roleName, String description) {
        var roleToCreate = new RoleEntity();
        roleToCreate.setName(roleName);
        roleToCreate.setDescription(description);
        RoleEntity savedRole = roleRepository.save(roleToCreate);
        log.info("Role '{}' criada com sucesso: {}", roleName, savedRole);
    }
}