package br.com.juhmaran.petshop_api.core.exceptions.runtimes;

public class PetShopUnauthorizedException extends RuntimeException {

    public PetShopUnauthorizedException(String message) {
        super(message);
    }

    public PetShopUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
