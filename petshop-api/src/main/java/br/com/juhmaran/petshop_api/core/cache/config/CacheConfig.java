package br.com.juhmaran.petshop_api.core.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Configuração de cache para a aplicação.
 * <p>
 * Esta classe configura o gerenciador de cache e o gerador de chaves de cache.
 * </p>
 *
 * <p><b>Exemplo de utilização:</b></p>
 * <pre>{@code
 * @Autowired
 * private CacheManager cacheManager;
 *
 * public void exemploDeUso() {
 *     Cache cache = cacheManager.getCache("default");
 *     cache.put("chave", "valor");
 *     String valor = cache.get("chave", String.class);
 *     System.out.println(valor); // Imprime "valor"
 * }
 * }</pre>
 *
 * <p>Esta classe utiliza o framework Spring para gerenciar o cache.</p>
 *
 * @author Juliane Maran
 */
@Slf4j
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        var cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setCacheNames(Collections.singletonList("default"));
        return cacheManager;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            var key = new StringBuilder();
            key.append(target.getClass().getSimpleName()).append("_");
            key.append(method.getName()).append("_");
            for (Object param : params) {
                key.append(param.toString()).append("_");
            }
            String generatedKey = key.toString();
            log.debug("Chave de cache gerada: {}", generatedKey);
            return generatedKey;
        };
    }
}
