package br.com.juhmaran.petshop_api.api.auth.login.services.impl;

import br.com.juhmaran.petshop_api.api.auth.login.dtos.LoginRequest;
import br.com.juhmaran.petshop_api.api.auth.login.dtos.TokenResponse;
import br.com.juhmaran.petshop_api.api.auth.login.services.AuthService;
import br.com.juhmaran.petshop_api.api.user.dto.response.UserResponse;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.api.user.mapping.UserMapper;
import br.com.juhmaran.petshop_api.api.user.repositories.UserRepository;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopBadRequestException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopNotFoundException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopUnauthorizedException;
import br.com.juhmaran.petshop_api.core.security.dtos.CustomUserDetails;
import br.com.juhmaran.petshop_api.core.security.services.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    ;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional(readOnly = true)
    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        if (isEmailInvalid(loginRequest.email())) {
            log.info("Tentativa de login para o email: {} em {}", loginRequest.email(), LocalDateTime.now());
            throw new PetShopNotFoundException("Email não encontrado: " + loginRequest.email());
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.email(), loginRequest.password()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.getToken(userDetails);

            var response = TokenResponse.builder()
                    .authToken(token)
                    .tokenType("Bearer")
                    .expiresIn(jwtUtil.getExpirationTime())
                    .username(userDetails.getUsername())
                    .name(userDetails.getUsername())
                    .roles(userDetails.getAuthorities().stream()
                            .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                            .toList())
                    .build();

            log.info("Login bem-sucedido para o email: {} em {}", loginRequest.email(), LocalDateTime.now());
            return response;

        } catch (BadCredentialsException e) {
            log.error("Senha inválida para o email: {} em {}", loginRequest.email(), LocalDateTime.now(), e);
            throw new PetShopBadRequestException("Senha inválida.");
        } catch (Exception e) {
            log.error("Erro inesperado durante o login para o email: {} em {}",
                    loginRequest.email(), LocalDateTime.now(), e);
            throw new PetShopInternalServerErrorException("Erro inesperado durante o processo de login.", e);
        }

    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
                log.warn("Não foi possível recuperar detalhes do usuário. Autenticação inválida ou não encontrada.");
                throw new PetShopUnauthorizedException("Usuário não autenticado.");
            }
            UserEntity user = userDetails.user();
            return UserMapper.INSTANCE.toUserResponse(user);
        } catch (AuthenticationException e) {
            throw new PetShopUnauthorizedException("Usuário não autenticado.");
        } catch (Exception e) {
            log.error("Erro inesperado ao obter usuário autenticado.", e);
            throw new PetShopInternalServerErrorException("Erro ao obter o usuário autenticado.");
        }
    }

    private boolean isEmailInvalid(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

}