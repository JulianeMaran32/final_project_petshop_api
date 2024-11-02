package br.com.juhmaran.petshop_api.core.security.configs;

import br.com.juhmaran.petshop_api.core.security.filters.CsrfCookieFilter;
import br.com.juhmaran.petshop_api.core.security.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static br.com.juhmaran.petshop_api.core.security.constants.SecurityConstants.*;

/**
 * Configuração de segurança da aplicação.
 * <p>
 * Esta classe configura a cadeia de filtros de segurança, incluindo CSRF, CORS, autenticação JWT e políticas de sessão.
 * </p>
 *
 * @author Juliane Maran
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configura a cadeia de filtros de segurança.
     *
     * @param http o objeto HttpSecurity
     * @return o bean SecurityFilterChain configurado
     * @throws Exception se ocorrer um erro durante a configuração
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configurando a cadeia de filtros de segurança");

        var requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.sessionManagement(session -> {
            log.debug("Configurando a política de criação de sessão para STATELESS");
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.csrf(csrf -> {
            log.debug("Configurando CSRF");
            csrf.csrfTokenRepository(csrfTokenRepository())
                    .csrfTokenRequestHandler(requestHandler)
                    .ignoringRequestMatchers(CLASSPATH)
                    .ignoringRequestMatchers(URL_API)
                    .ignoringRequestMatchers(URL_WEBJAR)
                    .ignoringRequestMatchers(URL_SWAGGER)
                    .ignoringRequestMatchers(URL_ERROR)
                    .ignoringRequestMatchers(URL_ACTUATOR)
                    .ignoringRequestMatchers(URL_CACHE)
                    .ignoringRequestMatchers(URL_AUTH)
                    .ignoringRequestMatchers(URL_CONTACT)
                    .ignoringRequestMatchers(URL_USER_REGISTER);
        });

        http.authorizeHttpRequests(authz -> {
            log.debug("Configurando autorizações de requisições HTTP");
            authz.requestMatchers("/public/**").permitAll()
                    .requestMatchers(URL_API).permitAll()
                    .requestMatchers(URL_WEBJAR).permitAll()
                    .requestMatchers(URL_ERROR).permitAll()
                    .requestMatchers(URL_SWAGGER).permitAll()
                    .requestMatchers(URL_ACTUATOR).permitAll()
                    .requestMatchers(URL_AUTH).permitAll()
                    .requestMatchers(URL_CONTACT).permitAll()
                    .requestMatchers(HttpMethod.GET, URL_CACHE).permitAll()
                    .requestMatchers(HttpMethod.POST, "/pets/register").hasRole("CUSTOMER")
                    .anyRequest().authenticated();
        });

        http.logout(logout -> {
            log.debug("Configurando logout");
            logout.logoutUrl("/auth/logout")
                    .invalidateHttpSession(true).deleteCookies(JSESSIONID, X_CSRF_TOKEN);
        });

        http.cors(cors -> {
            log.debug("Configurando CORS");
            cors.configurationSource(corsConfigurationSource());
        });

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        log.debug("Adicionando filtro de autenticação JWT antes do filtro de autenticação de usuário e senha");

        http.addFilterAfter(new CsrfCookieFilter(), UsernamePasswordAuthenticationFilter.class);
        log.debug("Adicionando filtro de CSRF após o filtro de autenticação de usuário e senha");

        return http.build();
    }

    /**
     * Configura a fonte de configuração CORS.
     *
     * @return a fonte de configuração CORS configurada
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(LOCALHOST_4200, LOCALHOST_9090, VIA_CEP, AZURE_API));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(AUTHORIZATION, CONTENT_TYPE, ACCEPT_LANGUAGE, X_CSRF_TOKEN));
        configuration.setExposedHeaders(Arrays.asList(AUTHORIZATION, CONTENT_TYPE, ACCEPT_LANGUAGE, X_CSRF_TOKEN));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configura o repositório de tokens CSRF.
     *
     * @return o repositório de tokens CSRF configurado
     */
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        var repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(X_CSRF_TOKEN);
        return repository;
    }

}