package br.com.juhmaran.petshop_api.core.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Juliane Maran
 */
@Slf4j
@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
public class CacheController {

    private final CacheManager cacheManager;

    @GetMapping("/contents")
    public Map<String, Object> getCacheContents() {
        Map<String, Object> cacheContents = new HashMap<>();
        for (String cacheName : cacheManager.getCacheNames()) {
            cacheContents.put(cacheName, getCacheEntries(cacheName));
        }
        log.info("Conteúdo do cache recuperado com sucesso.");
        return cacheContents;
    }

    private Map<Object, Object> getCacheEntries(String cacheName) {
        Map<Object, Object> cacheEntries = new HashMap<>();
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            try {
                @SuppressWarnings("unchecked")
                Map<Object, Object> nativeCache = (Map<Object, Object>) cache.getNativeCache();
                cacheEntries.putAll(nativeCache);
                log.debug("Entradas do cache '{}' recuperadas com sucesso.", cacheName);
            } catch (ClassCastException e) {
                log.error("Falha ao converter as entradas do cache: {}", cacheName, e);
            }
        } else {
            log.warn("Cache '{}' não encontrado.", cacheName);
        }
        return cacheEntries;
    }

}