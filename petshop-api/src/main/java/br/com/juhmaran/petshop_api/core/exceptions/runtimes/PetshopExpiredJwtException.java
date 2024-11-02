package br.com.juhmaran.petshop_api.core.exceptions.runtimes;

public class PetshopExpiredJwtException extends RuntimeException {

    public PetshopExpiredJwtException(String message) {
        super(message);
    }

    public PetshopExpiredJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
