package br.com.juhmaran.petshop_api.core.security.configs;

import br.com.juhmaran.petshop_api.core.security.filters.CsrfCookieFilter;
import br.com.juhmaran.petshop_api.core.security.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
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
 * @author Juliane Maran
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableGlobalAuthentication
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        var requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.csrf(csrf -> csrf
                .csrfTokenRepository(csrfTokenRepository())
                .csrfTokenRequestHandler(requestHandler)
                .ignoringRequestMatchers("/webjar/**", "/api/webjar/**")
                .ignoringRequestMatchers("/", "/**", "/api", "/api/**")
                .ignoringRequestMatchers("/api/swagger-ui*/**", "/api/swagger-ui/**",
                        "/api/swagger-ui.html", "/api/swagger-ui/api-docs.html", "/api/swagger-ui/index.html",
                        "/api/v3/api-docs", "/api/api-docs", "/api/swagger-ui*/*swagger-initializer.js")
                .ignoringRequestMatchers("/api/error", "/api/errors")
                .ignoringRequestMatchers("/api/actuator", "/api/actuator/**")
                .ignoringRequestMatchers("classpath:/META-INF/resources/", "classpath:/resources/",
                        "classpath:/static/", "classpath:/public/")
                .ignoringRequestMatchers("/cache/contents")
                .ignoringRequestMatchers("/auth/login", "/auth/forgot-password", "/auth/reset-password",
                        "/contacts", "/users/register"));

        http.authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.GET, "/public/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/webjar/**", "/api/webjar/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/", "/**", "/api", "/api/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/swagger-ui*/**", "/api/swagger-ui/**",
                        "/api/swagger-ui.html", "/api/swagger-ui/api-docs.html", "/api/swagger-ui/index.html",
                        "/api/v3/api-docs", "/api/api-docs", "/api/swagger-ui*/*swagger-initializer.js").permitAll()
                .requestMatchers("/api/error", "/api/errors").permitAll()
                .requestMatchers("/api/actuator", "/api/actuator/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/cache/contents").permitAll()
                .requestMatchers(HttpMethod.GET, "/addresses/cep/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/addresses/search").permitAll()
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/forgot-password").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/reset-password").permitAll()
                .requestMatchers(HttpMethod.POST, "/contacts").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                .anyRequest().authenticated());

//        http.logout(logout -> logout.logoutUrl("/auth/logout")
//                .invalidateHttpSession(true).deleteCookies(JSESSIONID, X_CSRF_TOKEN));
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new CsrfCookieFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(LOCALHOST_4200, LOCALHOST_9090, VIA_CEP));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(AUTHORIZATION, CONTENT_TYPE, ACCEPT_LANGUAGE, X_CSRF_TOKEN));
        configuration.setExposedHeaders(Arrays.asList(AUTHORIZATION, CONTENT_TYPE, ACCEPT_LANGUAGE, X_CSRF_TOKEN));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        var repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(X_CSRF_TOKEN);
        return repository;
    }

}