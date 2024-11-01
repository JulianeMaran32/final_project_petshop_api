package br.com.juhmaran.petshop_api.core.security.filters;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

/**
 * @author Juliane Maran
 */
@Slf4j
public class AuthoritiesLoggingAfterFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logAuthenticationDetails();
        chain.doFilter(request, response);
    }

    private void logAuthenticationDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            log.info("Usuário {} autenticado com sucesso e possui as autoridades {}",
                    authentication.getName(), authentication.getAuthorities());
        }
    }

}