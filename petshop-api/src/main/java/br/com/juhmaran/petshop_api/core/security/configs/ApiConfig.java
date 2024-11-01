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
 * @author Juliane Maran
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApiConfig {

    private final CustomUserDetailsService customUserDetailsService;


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        try {
//            log.info("Configurando AuthenticationManager");
//            return authenticationConfiguration.getAuthenticationManager();
//        } catch (Exception e) {
//            log.error("Erro ao configurar AuthenticationManager: {}", e.getMessage());
//            throw new PetShopInternalServerErrorException("Erro ao configurar AuthenticationManager", e);
//        }
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Configurando PasswordEncoder com BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        log.info("Configurando DaoAuthenticationProvider");
        var authProvider = createDaoAuthenticationProvider();
        log.info("DaoAuthenticationProvider configurado com sucesso");
        return authProvider;
    }

    private DaoAuthenticationProvider createDaoAuthenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}