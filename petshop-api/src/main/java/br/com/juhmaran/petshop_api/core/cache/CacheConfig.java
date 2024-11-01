package br.com.juhmaran.petshop_api.core.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @author Juliane Maran
 */
@Slf4j
@Configuration
@EnableCaching(proxyTargetClass = true)
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        var cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setCacheNames(Collections.singletonList("default"));
        log.info("CacheManager configurado com o cache 'default'");
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
