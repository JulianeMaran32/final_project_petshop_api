package br.com.juhmaran.petshop_api.core.security.filters;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static br.com.juhmaran.petshop_api.core.security.constants.SecurityConstants.XSRF_TOKEN;
import static br.com.juhmaran.petshop_api.core.security.constants.SecurityConstants.X_CSRF_TOKEN;

/**
 * Filtro que adiciona um cookie CSRF à resposta.
 * <p>
 * Este filtro verifica se um token CSRF está presente na solicitação e, se estiver, adiciona-o como um cookie na resposta.
 * </p>
 *
 * @author Juliane Maran
 */
@Slf4j
public class CsrfCookieFilter extends OncePerRequestFilter {

    /**
     * Processa a solicitação e a resposta, adicionando um cookie CSRF se um token CSRF estiver presente.
     *
     * @param request     A solicitação HTTP
     * @param response    A resposta HTTP
     * @param filterChain A cadeia de filtros
     * @throws ServletException Se ocorrer um erro de servlet
     * @throws IOException      Se ocorrer um erro de entrada/saída
     */
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {

            log.debug("Token CSRF encontrado: {}", csrfToken.getToken());

            if (csrfToken.getHeaderName() != null) {
                response.addHeader(csrfToken.getHeaderName(), csrfToken.getToken());
                log.debug("Cabeçalho CSRF adicionado: {} = {}", csrfToken.getHeaderName(), csrfToken.getToken());
            }

            if (csrfToken.getParameterName() != null) {
                response.addHeader(csrfToken.getParameterName(), csrfToken.getToken());
                log.debug("Parâmetro CSRF adicionado: {} = {}", csrfToken.getParameterName(), csrfToken.getToken());
            }

            if (csrfToken.getToken() != null) {
                response.addHeader(X_CSRF_TOKEN, csrfToken.getToken());
                log.debug("Cabeçalho X-CSRF-TOKEN adicionado: {}", csrfToken.getToken());
            }

            var csrfCookie = new Cookie(XSRF_TOKEN, csrfToken.getToken());
            csrfCookie.setPath("/");
            csrfCookie.setHttpOnly(false);
            csrfCookie.setSecure(request.isSecure());
            response.addCookie(csrfCookie);
            log.debug("Cookie XSRF-TOKEN adicionado: {}", csrfToken.getToken());

        } else {
            log.debug("Token CSRF não encontrado na solicitação.");
        }

        filterChain.doFilter(request, response);

    }

}
