package br.com.juhmaran.petshop_api.api.auth.login.services.impl;

import br.com.juhmaran.petshop_api.api.auth.login.dtos.LoginRequest;
import br.com.juhmaran.petshop_api.api.auth.login.dtos.TokenResponse;
import br.com.juhmaran.petshop_api.api.auth.login.services.AuthService;
import br.com.juhmaran.petshop_api.api.auth.login.utils.AuthUtil;
import br.com.juhmaran.petshop_api.api.user.dto.response.UserResponse;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.api.user.mapping.UserMapper;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopUnauthorizedException;
import br.com.juhmaran.petshop_api.core.security.dtos.CustomUserDetails;
import br.com.juhmaran.petshop_api.core.security.services.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final AuthUtil authUtil;

    @Transactional(readOnly = true)
    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authUtil.authenticateUser(loginRequest);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());
            return authUtil.buildTokenResponse(userDetails, token);
        } catch (BadCredentialsException e) {
            log.error("Senha inválida para o email: {}", loginRequest.email());
            authUtil.handleFailedLoginAttempt(loginRequest.email());
            throw new PetShopUnauthorizedException("Senha inválida.");
        } catch (Exception e) {
            log.error("Erro inesperado durante o login para o email: {}", loginRequest.email(), e);
            throw new PetShopInternalServerErrorException("Erro inesperado durante o processo de login.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getCurrentUser() {
        log.info("Obtendo usuário autenticado.");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = authUtil.getAuthenticatedUserDetails(authentication);
            UserEntity user = userDetails.user();
            log.info("Usuário autenticado: {}", user.getEmail());
            return UserMapper.INSTANCE.toUserResponse(user);
        } catch (AuthenticationException e) {
            throw new PetShopUnauthorizedException("Usuário não autenticado.");
        } catch (Exception e) {
            log.error("Erro inesperado ao obter usuário autenticado.", e);
            throw new PetShopInternalServerErrorException("Erro ao obter o usuário autenticado.");
        }
    }

}