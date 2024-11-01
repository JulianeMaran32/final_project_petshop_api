package br.com.juhmaran.petshop_api.api.auth.login.utils;

import br.com.juhmaran.petshop_api.api.auth.login.dtos.LoginRequest;
import br.com.juhmaran.petshop_api.api.auth.login.dtos.TokenResponse;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.api.user.repositories.UserRepository;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopNotFoundException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopUnauthorizedException;
import br.com.juhmaran.petshop_api.core.security.dtos.CustomUserDetails;
import br.com.juhmaran.petshop_api.core.security.services.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AuthUtil {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public void validateEmail(String email) {
        log.info("Validando email: {}", email);
        if (isEmailInvalid(email)) {
            log.warn("Email não encontrado: {}", email);
            throw new PetShopNotFoundException("Email não encontrado");
        }
    }

    public Authentication authenticateUser(LoginRequest loginRequest) {
        log.info("Autenticando usuário com email: {}", loginRequest.email());
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
    }

    public TokenResponse buildTokenResponse(UserDetails userDetails, String token) {
        log.info("Construindo resposta de token para usuário: {}", userDetails.getUsername());
        return new TokenResponse(
                token,
                "Bearer",
                jwtUtil.getExpirationTime(),
                userDetails.getUsername(),
                ((CustomUserDetails) userDetails).user().getName(),
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList());
    }

    public boolean isEmailInvalid(String email) {
        try {
            boolean emailInvalid = userRepository.findByEmail(email).isEmpty();
            if (emailInvalid) {
                log.warn("Email inválido: {}", email);
            }
            return emailInvalid;
        } catch (Exception e) {
            throw new PetShopInternalServerErrorException("Erro ao verificar email.");
        }
    }

    public void handleFailedLoginAttempt(String email) {
        log.warn("Tentativa de login falhou para o email: {}", email);
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new PetShopNotFoundException("Usuário não encontrado."));
        int failedAttempts = user.getFailedLoginAttempts() + 1;
        userRepository.updateFailedLoginAttempts(failedAttempts, email);

        if (failedAttempts >= 5) {
            log.error("Conta bloqueada devido a 5 tentativas de login falhadas para o email: {}", email);
            userRepository.updateAccountNonLocked(false, email);
            throw new PetShopUnauthorizedException("Account is locked due to 5 failed login attempts");
        }
    }

    public void resetFailedLoginAttempts(String email) {
        log.info("Resetando tentativas de login falhadas para o email: {}", email);
        userRepository.updateFailedLoginAttempts(0, email);
    }

    public CustomUserDetails getAuthenticatedUserDetails(Authentication authentication) {
        log.info("Obtendo detalhes do usuário autenticado.");
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            log.warn("Usuário não autenticado.");
            throw new PetShopUnauthorizedException("Usuário não autenticado.");
        }
        return userDetails;
    }

}
