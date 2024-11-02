package br.com.juhmaran.petshop_api.core.security.configs;

import br.com.juhmaran.petshop_api.core.security.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuração da API para segurança.
 * <p>
 * Esta classe configura os beans necessários para a autenticação e codificação de senhas.
 * </p>
 *
 * @author Juliane Maran
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApiConfig {

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Cria e configura o bean AuthenticationManager.
     *
     * @param http o objeto HttpSecurity
     * @return o bean AuthenticationManager configurado
     * @throws Exception se ocorrer um erro durante a configuração
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    /**
     * Cria e configura o bean PasswordEncoder.
     *
     * @return o bean PasswordEncoder configurado
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Cria e configura o bean DaoAuthenticationProvider.
     *
     * @return o bean DaoAuthenticationProvider configurado
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        return createDaoAuthenticationProvider();
    }

    /**
     * Cria e configura uma instância de DaoAuthenticationProvider.
     *
     * @return uma instância de DaoAuthenticationProvider configurada
     */
    private DaoAuthenticationProvider createDaoAuthenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}