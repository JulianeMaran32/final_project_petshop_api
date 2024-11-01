package br.com.juhmaran.petshop_api.core.security.filters;

import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopBadRequestException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Juliane Maran
 */
@Slf4j
public class CsrfCookieFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            CsrfToken csrfToken = getCsrfToken(request);
            addCsrfTokenToResponse(response, csrfToken);
            filterChain.doFilter(request, response);
            log.info("Filtro CsrfCookieFilter executado com sucesso.");
        } catch (PetShopBadRequestException e) {
            log.error("Erro de solicitação: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado durante o processamento do filtro CSRF: {}", e.getMessage());
            throw new PetShopInternalServerErrorException("Erro inesperado durante o processamento do filtro CSRF.", e);
        }
    }

    private CsrfToken getCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken == null || csrfToken.getHeaderName() == null) {
            log.warn("Token CSRF não encontrado na solicitação.");
            throw new PetShopBadRequestException("Token CSRF não encontrado na solicitação.");
        }
        log.info("Token CSRF encontrado: {}", csrfToken.getToken());
        return csrfToken;
    }

    private void addCsrfTokenToResponse(HttpServletResponse response, CsrfToken csrfToken) {
        response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        log.info("Token CSRF adicionado ao cabeçalho da resposta: {}", csrfToken.getToken());
    }

}
