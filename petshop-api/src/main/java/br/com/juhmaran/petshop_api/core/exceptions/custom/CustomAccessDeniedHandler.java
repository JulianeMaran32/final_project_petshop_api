package br.com.juhmaran.petshop_api.core.exceptions.custom;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Juliane Maran
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        setResponseAttributes(response);
        writeErrorResponse(response);
    }

    private void setResponseAttributes(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
    }

    private void writeErrorResponse(HttpServletResponse response) throws IOException {
        response.getWriter().write("{\"error\": \"Access Denied\"}");
    }

}