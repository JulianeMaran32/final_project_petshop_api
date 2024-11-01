package br.com.juhmaran.petshop_api.core.exceptions.runtimes;

public class PetShopNotFoundException extends RuntimeException {

    public PetShopNotFoundException(String message) {
        super(message);
    }

    public PetShopNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PetShopNotFoundException(String message, Long id) {
        super(message + " (id: " + id + ")");
    }

}
