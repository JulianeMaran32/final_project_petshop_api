package br.com.juhmaran.petshop_api.core.exceptions.runtimes;

public class PetShopBadRequestException extends RuntimeException {

    public PetShopBadRequestException(String message) {
        super(message);
    }

    public PetShopBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
