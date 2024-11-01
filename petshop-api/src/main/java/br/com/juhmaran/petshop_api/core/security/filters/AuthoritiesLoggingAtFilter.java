package br.com.juhmaran.petshop_api.core.security.filters;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author Juliane Maran
 */
@Slf4j
public class AuthoritiesLoggingAtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logAuthenticationValidation();
        chain.doFilter(request, response);
    }

    private void logAuthenticationValidation() {
        log.info("Validação de autenticação em andamento");
    }

}
