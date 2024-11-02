package br.com.juhmaran.petshop_api.core.security.filters;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Filtro que registra a validação de autenticação durante a execução da cadeia de filtros.
 * <p>
 * Este filtro é responsável por registrar a validação de autenticação do usuário durante a execução da cadeia de filtros.
 * </p>
 *
 * @author Juliane Maran
 */
@Slf4j
public class AuthoritiesLoggingAtFilter implements Filter {

    /**
     * Processa a solicitação e a resposta, passando-as para o próximo filtro na cadeia.
     *
     * @param request  A solicitação a ser processada
     * @param response A resposta associada à solicitação
     * @param chain    Fornece acesso ao próximo filtro na cadeia para que este filtro possa passar a solicitação e a resposta
     *                 para processamento adicional
     * @throws IOException      Se ocorrer um erro de entrada/saída
     * @throws ServletException Se ocorrer um erro de servlet
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logAuthenticationValidation();
        chain.doFilter(request, response);
    }

    /**
     * Registra a validação de autenticação do usuário.
     */
    private void logAuthenticationValidation() {
        log.info("Validação de autenticação em andamento");
    }

}
