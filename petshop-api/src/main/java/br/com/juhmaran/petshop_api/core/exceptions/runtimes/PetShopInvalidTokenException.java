package br.com.juhmaran.petshop_api.core.exceptions.runtimes;

public class PetShopInvalidTokenException extends RuntimeException {

    public PetShopInvalidTokenException(String message) {
        super(message);
    }

  public PetShopInvalidTokenException(String message, Throwable cause) {
    super(message, cause);
  }
}
