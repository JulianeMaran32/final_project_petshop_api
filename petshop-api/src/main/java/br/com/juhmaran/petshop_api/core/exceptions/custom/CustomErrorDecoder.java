package br.com.juhmaran.petshop_api.core.exceptions.custom;

import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopBadRequestException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Decodificador de erros personalizado para lidar com respostas de erro do Feign.
 * <p>
 * Esta classe implementa a interface {@link feign.codec.ErrorDecoder} e fornece
 * uma lógica personalizada para decodificar respostas de erro HTTP.
 * </p>
 *
 * @author Juliane Maran
 */
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    /**
     * Decodifica a resposta de erro HTTP e retorna uma exceção apropriada.
     *
     * @param methodKey a chave do metodo que foi chamado
     * @param response  a resposta HTTP recebida
     * @return uma exceção que representa o erro
     */
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