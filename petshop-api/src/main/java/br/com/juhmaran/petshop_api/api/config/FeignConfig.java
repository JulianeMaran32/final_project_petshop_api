package br.com.juhmaran.petshop_api.api.config;

import br.com.juhmaran.petshop_api.core.exceptions.custom.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração para o Feign Client.
 * <p>
 * Esta classe define um bean para o decodificador de erros personalizado.
 * </p>
 */
@Configuration
public class FeignConfig {

    /**
     * Cria um bean para o decodificador de erros personalizado.
     *
     * @return uma instância de {@link CustomErrorDecoder}
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

}