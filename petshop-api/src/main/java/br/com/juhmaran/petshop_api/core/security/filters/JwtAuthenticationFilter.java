package br.com.juhmaran.petshop_api.core.security.filters;

import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopForbiddenException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopUnauthorizedException;
import br.com.juhmaran.petshop_api.core.security.services.CustomUserDetailsService;
import br.com.juhmaran.petshop_api.core.security.services.JwtUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static br.com.juhmaran.petshop_api.core.security.constants.SecurityConstants.AUTHORIZATION;
import static br.com.juhmaran.petshop_api.core.security.constants.SecurityConstants.BEARER;

/**
 * @author Juliane Maran
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain chain)
            throws ServletException, IOException {
        log.info("Iniciando filtro de autenticação JWT.");
        try {
            final String authorizationHeader = request.getHeader(AUTHORIZATION);
            log.debug("Cabeçalho de autorização: {}", authorizationHeader);

            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.getUsernameFromToken(jwt);
                log.debug("Token JWT extraído: {}", jwt);
                log.debug("Nome de usuário extraído do token: {}", username);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                log.debug("Detalhes do usuário carregados: {}", userDetails.getUsername());

                if (jwtUtil.validateToken(jwt)) {
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    log.info("Autenticação bem-sucedida para o usuário: {}", username);
                }
            }
            chain.doFilter(request, response);
            log.info("Filtro de autenticação JWT concluído.");
        } catch (AccessDeniedException e) {
            log.error("Acesso negado: {}", e.getMessage());
            throw new PetShopUnauthorizedException("Usuário não possui permissão.", e);
        } catch (AuthenticationException e) {
            log.error("Falha na autenticação: {}", e.getMessage());
            throw new PetShopForbiddenException("Usuário não autenticado.", e);
        } catch (Exception e) {
            log.error("Erro inesperado no filtro de autenticação JWT: {}", e.getMessage());
            throw new ServletException("Erro inesperado durante o processamento do filtro de autenticação JWT.", e);
        }
    }

}
