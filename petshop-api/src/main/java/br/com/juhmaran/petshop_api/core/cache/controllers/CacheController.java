package br.com.juhmaran.petshop_api.core.cache.controllers;

import br.com.juhmaran.petshop_api.core.cache.services.CacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
@Tag(name = "Cache", description = "Controlador para operações de cache. Este controlador fornece endpoints para interagir com o cache da aplicação.")
public class CacheController {

    private final CacheService cacheService;

    @Operation(summary = "Recupera o cache", operationId = "getCacheContents", tags = {"Cache"},
            description = "Recuperação do conteúdo do cache.")
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/contents")
    public Map<String, Object> getCacheContents() {
        Map<String, Object> cacheContents = cacheService.getCacheContents();
        log.info("Conteúdo do cache recuperado com sucesso.");
        return cacheContents;
    }

}