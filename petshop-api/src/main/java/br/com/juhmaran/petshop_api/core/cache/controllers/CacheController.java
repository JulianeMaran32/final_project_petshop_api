package br.com.juhmaran.petshop_api.core.cache.controllers;

import br.com.juhmaran.petshop_api.core.cache.services.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controlador para operações de cache.
 * <p>
 * Este controlador fornece endpoints para interagir com o cache da aplicação.
 * </p>
 * <p>Esta classe utiliza o framework Spring para gerenciar o cache.</p>
 * <p>Responsabilidade: Expor endpoints para operações de cache.</p>
 * <p>Princípio da Responsabilidade Única: Esta classe lida apenas com a exposição de endpoints.</p>
 * <p>As operações de recuperação de entradas de cache foram movidas para a classe CacheService.</p>
 * <p>Isso garante que cada classe tenha uma única responsabilidade.</p>
 *
 * @author Juliane Maran
 */
@Slf4j
@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    /**
     * Recupera o conteúdo do cache.
     *
     * @return um mapa contendo os nomes dos caches e suas respectivas entradas
     */
    @GetMapping("/contents")
    public Map<String, Object> getCacheContents() {
        Map<String, Object> cacheContents = cacheService.getCacheContents();
        log.info("Conteúdo do cache recuperado com sucesso.");
        return cacheContents;
    }

}