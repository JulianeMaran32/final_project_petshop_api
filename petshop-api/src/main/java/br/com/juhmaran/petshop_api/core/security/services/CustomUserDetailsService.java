package br.com.juhmaran.petshop_api.core.security.services;

import br.com.juhmaran.petshop_api.core.security.dtos.CustomUserDetails;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.api.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serviço de detalhes do usuário personalizado.
 * <p>
 * Este serviço carrega os detalhes do usuário com base no e-mail fornecido.
 * </p>
 *
 * @author Juliane Maran
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Carrega o usuário pelo e-mail.
     *
     * @param email o e-mail do usuário
     * @return os detalhes do usuário
     * @throws UsernameNotFoundException se o usuário não for encontrado
     */
    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Tentativa de carregar usuário com e-mail: {}", email);
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado com e-mail: {}", email);
                    return new UsernameNotFoundException("Usuário não encontrado com e-mail: " + email);
                });
        log.info("Usuário encontrado: {}", user.getEmail());
        return new CustomUserDetails(user);
    }

}