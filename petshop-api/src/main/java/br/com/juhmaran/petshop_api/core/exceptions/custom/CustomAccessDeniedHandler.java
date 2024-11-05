package br.com.juhmaran.petshop_api.core.exceptions.custom;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Manipulador personalizado para lidar com exceções de acesso negado.
 * Define a resposta HTTP com status 403 e uma mensagem de erro em formato JSON.
 *
 * @author Juliane Maran
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Manipula a exceção de acesso negado.
     *
     * @param request               a solicitação HTTP
     * @param response              a resposta HTTP
     * @param accessDeniedException a exceção de acesso negado
     * @throws IOException se ocorrer um erro de entrada/saída
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        setResponseAttributes(response);
        writeErrorResponse(response);
    }

    /**
     * Define os atributos da resposta HTTP.
     *
     * @param response a resposta HTTP
     */
    private void setResponseAttributes(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
    }

    /**
     * Escreve a mensagem de erro na resposta HTTP.
     *
     * @param response a resposta HTTP
     * @throws IOException se ocorrer um erro de entrada/saída
     */
    private void writeErrorResponse(HttpServletResponse response) throws IOException {
        response.getWriter().write("{\"error\": \"Access Denied\"}");
    }

}