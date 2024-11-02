package br.com.juhmaran.petshop_api.core.exceptions;

import br.com.juhmaran.petshop_api.core.exceptions.dtos.ErrorResponse;
import br.com.juhmaran.petshop_api.core.exceptions.dtos.ValidationErrorResponse;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.*;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Manipulador global de exceções para a aplicação.
 *
 * <p>Esta classe captura e trata exceções lançadas pelos controladores REST,
 * retornando respostas HTTP apropriadas com mensagens de erro.</p>
 *
 * <p><b>Exemplo de uso:</b></p>
 * <pre>{@code
 * @RestController
 * public class ProdutoController {
 *
 *     @GetMapping("/produtos/{id}")
 *     public ResponseEntity<Produto> getProduto(@PathVariable Long id) {
 *         Produto produto = produtoService.findById(id)
 *                 .orElseThrow(() -> new PetShopNotFoundException("Produto não encontrado"));
 *         return ResponseEntity.ok(produto);
 *     }
 * }
 * }</pre>
 *
 * <p>Esta classe utiliza o framework Spring para capturar exceções e o Lombok para logging.</p>
 *
 * @author Juliane Maran
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Erro: Falha na validação dos campos - {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        var errorResponse = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "A validação falhou para um ou mais argumentos.",
                errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("Erro: Parâmetro ausente - {}", ex.getMessage());
        var errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Parâmetro de solicitação ausente.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Erro: Mensagem HTTP não legível - {}", ex.getMessage());
        var errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Mensagem HTTP não legível.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("Erro: Nenhum manipulador encontrado - {}", ex.getMessage());
        var errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Nenhum manipulador encontrado para a solicitação.");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, "Usuário não autenticado");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return buildErrorResponse(ex, HttpStatus.FORBIDDEN, "Usuário não possui permissão");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return buildErrorResponse(ex, HttpStatus.METHOD_NOT_ALLOWED, "Método de solicitação HTTP não suportado.");
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_ACCEPTABLE, "Tipo de mídia HTTP não aceitável.");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return buildErrorResponse(ex, HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Tipo de mídia não suportada.");
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotWritableException(HttpMessageNotWritableException ex) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Mensagem HTTP não gravável.");
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorResponse> handleServletException(ServletException ex) {
        log.error("Erro: Exceção do Servlet - {}", ex.getMessage());
        var errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.");
    }

    @ExceptionHandler(PetShopBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(PetShopBadRequestException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Requisição inválida.");
    }

    @ExceptionHandler(PetShopUnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(PetShopUnauthorizedException ex) {
        log.error("Erro: Usuário não autenticado - {}", ex.getMessage());
        var errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "Usuário não autenticado.");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PetshopExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(PetshopExpiredJwtException ex) {
        log.error("Erro: JWT token expirado - {}", ex.getMessage());
        var errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "JWT token expirado.");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(PetShopForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(PetShopForbiddenException ex) {
        log.error("Erro: Usuário não possui permissão - {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                "Usuário não possui permissão.");
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PetShopNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(PetShopNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, "Recurso não encontrado.");
    }

    @ExceptionHandler(PetShopInternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(PetShopInternalServerErrorException ex) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Argumento Inválido!");
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, String message) {
        logException(ex.getClass().getSimpleName(), ex);
        var errorResponse = new ErrorResponse(status.value(), status.getReasonPhrase(), message);
        return new ResponseEntity<>(errorResponse, status);
    }

    private void logException(String methodName, Exception ex) {
        log.error("Exception in {}.{}: {}", this.getClass().getSimpleName(), methodName, ex.getClass().getSimpleName());
    }

}
