package br.com.juhmaran.petshop_api.core.cache.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Serviço para operações de cache.
 * <p>
 * Esta classe lida com a lógica de recuperação de entradas de cache.
 * </p>
 * <p>Responsabilidade: Recuperar entradas de cache.</p>
 * <p>Princípio da Responsabilidade Única: Esta classe lida apenas com a recuperação de entradas de cache.</p>
 * <p>Autor: Juliane Maran</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;

    /**
     * Recupera o conteúdo do cache.
     *
     * @return um mapa contendo os nomes dos caches e suas respectivas entradas
     */
    public Map<String, Object> getCacheContents() {
        Map<String, Object> cacheContents = new HashMap<>();
        for (String cacheName : cacheManager.getCacheNames()) {
            cacheContents.put(cacheName, getCacheEntries(cacheName));
        }
        return cacheContents;
    }

    /**
     * Recupera as entradas de um cache específico.
     *
     * @param cacheName o nome do cache
     * @return um mapa contendo as entradas do cache
     */
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
