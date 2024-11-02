package br.com.juhmaran.petshop_api.core.security.filters;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

/**
 * Filtro que registra detalhes de autenticação após a execução da cadeia de filtros.
 * <p>
 * Este filtro é responsável por registrar os detalhes de autenticação do usuário após a execução da cadeia de filtros.
 * </p>
 *
 * @author Juliane Maran
 */
@Slf4j
public class AuthoritiesLoggingAfterFilter implements Filter {

    /**
     * Processa a solicitação e a resposta, passando-as para o próximo filtro na cadeia.
     *
     * @param request  A solicitação a ser processada
     * @param response A resposta associada à solicitação
     * @param chain    Fornece acesso ao próximo filtro na cadeia para que este filtro possa passar a solicitação e a resposta
     *                 para processamento adicional
     *
     * @throws IOException       Se ocorrer um erro de entrada/saída
     * @throws ServletException  Se ocorrer um erro de servlet
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logAuthenticationDetails();
        chain.doFilter(request, response);
    }

    /**
     * Registra os detalhes de autenticação do usuário.
     */
    private void logAuthenticationDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            log.info("Usuário {} autenticado com sucesso e possui as autoridades {}",
                    authentication.getName(), authentication.getAuthorities());
        }
    }

}