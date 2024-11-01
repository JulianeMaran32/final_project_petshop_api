package br.com.juhmaran.petshop_api.core.exceptions.runtimes;

public class PetShopInternalServerErrorException extends RuntimeException {

    public PetShopInternalServerErrorException(String message) {
        super(message);
    }

    public PetShopInternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
