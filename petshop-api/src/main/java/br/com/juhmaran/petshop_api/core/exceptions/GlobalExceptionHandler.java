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
 * @author Juliane Maran
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logException("handleValidationExceptions", ex);
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
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Parâmetro de solicitação ausente.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Mensagem HTTP não legível.");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, "Nenhum manipulador encontrado para a solicitação.");
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
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento da solicitação.");
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
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, "Usuário não autenticado");
    }

    @ExceptionHandler(PetShopForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(PetShopForbiddenException ex) {
        return buildErrorResponse(ex, HttpStatus.FORBIDDEN, "Usuário não possui permissão");
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
