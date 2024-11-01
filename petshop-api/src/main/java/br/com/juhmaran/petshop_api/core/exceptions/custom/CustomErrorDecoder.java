package br.com.juhmaran.petshop_api.core.exceptions.custom;

import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopBadRequestException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juliane Maran
 */
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Erro ao chamar o método {}: status {}, motivo {}", methodKey, response.status(), response.reason());
        return switch (response.status()) {
            case 400 ->
                    new PetShopBadRequestException("A requisição enviada é inválida. Por favor, verifique os dados e tente novamente.");
            case 404 ->
                    new PetShopNotFoundException("O recurso solicitado não foi encontrado. Verifique a URL ou o identificador e tente novamente.");
            case 500 ->
                    new PetShopInternalServerErrorException("Ocorreu um erro interno no servidor. Tente novamente mais tarde.");
            default -> new Exception("Ocorreu um erro inesperado. Por favor, tente novamente.");
        };
    }

}