package br.com.juhmaran.petshop_api.core.exceptions.runtimes;

public class PetShopConflictException extends RuntimeException {

    public PetShopConflictException(String message) {
        super(message);
    }

    public PetShopConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
