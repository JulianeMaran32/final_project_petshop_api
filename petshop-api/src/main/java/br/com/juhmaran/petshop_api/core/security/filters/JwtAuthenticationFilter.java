package br.com.juhmaran.petshop_api.core.security.filters;

import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetshopExpiredJwtException;
import br.com.juhmaran.petshop_api.core.security.services.CustomUserDetailsService;
import br.com.juhmaran.petshop_api.core.security.services.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticação JWT.
 * <p>
 * Este filtro intercepta as solicitações HTTP para verificar a presença de um token JWT e autenticar o usuário.
 * </p>
 *
 * @author Juliane Maran
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    /**
     * Processa a solicitação HTTP para verificar a presença de um token JWT e autenticar o usuário.
     *
     * @param request  A solicitação HTTP
     * @param response A resposta HTTP
     * @param chain    A cadeia de filtros
     * @throws ServletException Se ocorrer um erro de servlet
     * @throws IOException      Se ocorrer um erro de entrada/saída
     */
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain chain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null) {
            try {
                processTokenAuthentication(token, request);
            } catch (ExpiredJwtException e) {
                log.warn("Token JWT expirado");
                throw new PetshopExpiredJwtException("O token JWT está expirado");
            } catch (PetShopInternalServerErrorException e) {
                log.error("Token JWT inválido: {}", e.getMessage());
                throw new PetShopInternalServerErrorException("Token JWT inválido", e);
            } catch (Exception e) {
                log.error("Erro inesperado ao processar o token JWT: {}", e.getMessage());
                throw new PetShopInternalServerErrorException("Erro inesperado ao processar o token JWT", e);
            }
        }

        chain.doFilter(request, response);

    }

    /**
     * Extrai o token JWT do cabeçalho de autorização da solicitação.
     *
     * @param request A solicitação HTTP
     * @return O token JWT, ou null se o cabeçalho de autorização não estiver presente ou não começar com "Bearer "
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        log.debug("Cabeçalho de autorização: {}", authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    /**
     * Processa a autenticação do token JWT.
     *
     * @param token   O token JWT
     * @param request A solicitação HTTP
     */
    private void processTokenAuthentication(String token, HttpServletRequest request) {
        String username = jwtUtil.extractUsername(token);
        log.debug("Nome de usuário extraído do token: {}", username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.debug("Carregando detalhes do usuário para: {}", username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails)) {
                log.info("Token JWT validado com sucesso para o usuário: {}", userDetails.getUsername());
                setAuthentication(userDetails, request);
            }
        }
    }

    /**
     * Define a autenticação no contexto de segurança.
     *
     * @param userDetails Os detalhes do usuário autenticado
     * @param request     A solicitação HTTP
     */
    private void setAuthentication(UserDetails userDetails, HttpServletRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
