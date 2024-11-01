package br.com.juhmaran.petshop_api.api.user.services.impl;

import br.com.juhmaran.petshop_api.api.user.dto.request.UserRegistrationRequest;
import br.com.juhmaran.petshop_api.api.user.dto.request.UserUpdateRequest;
import br.com.juhmaran.petshop_api.api.user.dto.response.UserResponse;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.api.user.mapping.UserMapper;
import br.com.juhmaran.petshop_api.api.user.repositories.UserRepository;
import br.com.juhmaran.petshop_api.api.user.services.UserService;
import br.com.juhmaran.petshop_api.api.user.utils.UserUtil;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserUtil userUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserResponse registerUser(UserRegistrationRequest registrationDto) {
        try {
            log.info("Iniciando verificação de email para o usuário: {}", registrationDto.getEmail());
            userUtil.verifyEmail(registrationDto);

            log.info("Iniciando verificação de CPF para o usuário: {}", registrationDto.getCpf());
            userUtil.verifyCpf(registrationDto);

            log.info("Iniciando verificação de senha para o usuário.");
            UserUtil.checkPassword(registrationDto);

            log.info("Codificando senha para o usuário.");
            String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());

            log.info("Mapeando UserRegistrationRequest para UserEntity.");
            var user = UserUtil.mapToUserEntity(registrationDto, encodedPassword);

            log.info("Definindo valores de auditoria.");
            user.setCreatedBy(registrationDto.getName());
            user.setCreatedByRole(registrationDto.getRole());
            user.setCreatedDate(LocalDateTime.now());

            log.info("Convertendo string de função para RoleType.");
            userUtil.convertStringRoleToRoleType(registrationDto, user);

            log.info("Salvando usuário no repositório.");
            UserEntity savedUser = userRepository.save(user);

            log.info("Usuário registrado com sucesso: {}", savedUser.getId());
            return UserMapper.INSTANCE.toUserResponse(savedUser);
        } catch (PetShopConflictException e) {
            log.error("Erro de conflito ao registrar usuário: {}", e.getMessage());
            throw new PetShopConflictException("Erro de conflito ao registrar usuário.");
        } catch (PetShopBadRequestException e) {
            log.error("Erro de solicitação incorreta ao registrar usuário: {}", e.getMessage());
            throw new PetShopBadRequestException("Erro de solicitação incorreta ao registrar usuário.");
        } catch (PetShopInternalServerErrorException e) {
            log.error("Erro interno do servidor ao registrar usuário: {}", e.getMessage());
            throw new PetShopInternalServerErrorException("Erro ao cadastrar usuário.");
        }
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        try {
            Authentication authentication = UserUtil.getAuthentication();
            UserEntity authenticatedUser = userUtil.getUserEntity(authentication);
            UserUtil.userNotPermission(authenticatedUser, id,
                    "Usuário não tem permissão para atualizar dados de outro usuário.");
            UserEntity userEntity = userUtil.getUserEntity(id);
            userUtil.userUpdate(id, userUpdateRequest, userEntity);
            userRepository.save(userEntity);
            return UserMapper.INSTANCE.toUserResponse(userEntity);
        } catch (PetShopConflictException e) {
            log.error("Erro de conflito ao atualizar usuário: {}", e.getMessage());
            throw new PetShopConflictException("Dados já cadastrados.");
        } catch (PetShopBadRequestException e) {
            log.error("Erro de solicitação incorreta ao atualizar usuário: {}", e.getMessage());
            throw new PetShopBadRequestException("Campo inválido.");
        } catch (PetShopNotFoundException e) {
            log.error("Erro de não encontrado ao registrar usuário: {}", e.getMessage());
            throw new PetShopNotFoundException("Usuário não encontrado.");
        } catch (Exception e) {
            log.error("Erro interno do servidor ao atualizar usuário: {}", e.getMessage());
            throw new PetShopInternalServerErrorException("Erro ao atualizar usuário.");
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            Authentication authentication = UserUtil.getAuthentication();
            UserEntity authenticatedUser = userUtil.getUserEntity(authentication);
            UserUtil.userNotPermission(authenticatedUser, id,
                    "Usuário não tem permissão para excluir a conta de outro usuário.");
            userRepository.delete(authenticatedUser);
        } catch (PetShopBadRequestException e) {
            log.error("Erro de solicitação incorreta ao excluir usuário: {}", e.getMessage());
            throw new PetShopBadRequestException("Erro de solicitação incorreta ao excluir usuário.");
        } catch (Exception e) {
            log.error("Erro ao excluir usuário: {}", e.getMessage());
            throw new PetShopInternalServerErrorException("Erro ao excluir usuário", e);
        }
    }

    @Override
    public void unlockAccount(String email) {
        try {
            userRepository.updateFailedLoginAttempts(0, email);
            userRepository.updateAccountNonLocked(true, email);
        } catch (Exception e) {
            log.error("Erro ao desbloquear a conta: {}", e.getMessage());
            throw new PetShopInternalServerErrorException("Erro ao desbloquear a conta", e);
        }
    }

    @Override
    public List<UserResponse> searchUsers(String name, String cpf, String email, String phone) {
        try {
            Authentication authentication = UserUtil.getAuthentication();
            UserEntity authenticatedUser = userUtil.getUserEntity(authentication);
            UserUtil.checkUserRole(authenticatedUser, List.of("SUPER_ADMIN", "ADMIN"));

            List<UserEntity> users = userRepository.searchUsers(name, cpf, email, phone);
            return users.stream()
                    .map(UserMapper.INSTANCE::toUserResponse)
                    .toList();
        } catch (PetShopUnauthorizedException e) {
            log.error("Usuários {} não autenticado", e.getMessage());
            throw new PetShopUnauthorizedException("Usuário não autenticado .");
        } catch (PetShopForbiddenException e) {
            log.error("Usuários {} não tem permissão", e.getMessage());
            throw new PetShopForbiddenException("Usuário não tem permissão.");
        } catch (Exception e) {
            log.error("Erro ao buscar usuários: {}", e.getMessage());
            throw new PetShopInternalServerErrorException("Erro ao buscar usuários", e);
        }
    }

}
