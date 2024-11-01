package br.com.juhmaran.petshop_api.core.exceptions.runtimes;

public class PetShopForbiddenException extends RuntimeException {

  public PetShopForbiddenException(String message) {
    super(message);
  }

  public PetShopForbiddenException(String message, Throwable cause) {
    super(message, cause);
  }
}
