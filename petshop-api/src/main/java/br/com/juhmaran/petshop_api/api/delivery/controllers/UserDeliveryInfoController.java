package br.com.juhmaran.petshop_api.api.delivery.controllers;

import br.com.juhmaran.petshop_api.api.delivery.dto.UserDeliveryInfoView;
import br.com.juhmaran.petshop_api.api.delivery.services.UserDeliveryInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
@Tag(name = "Delivery", description = "API para consulta de informações de entrega")
public class UserDeliveryInfoController {

    private final UserDeliveryInfoService userDeliveryInfoServiceImpl;

    @Operation(summary = "Busca informações de entrega", operationId = "findUserDeliveryInfo", tags = {"Delivery"},
            description = "Busca informações de entrega por nome, telefone e/ou CEP",
            security = {@SecurityRequirement(name = "bearer")})
    @ApiResponse(responseCode = "200", description = "Informações de entrega encontradas",
            content = @Content(schema = @Schema(implementation = UserDeliveryInfoView.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Não autenticado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Informação não encontrada",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/info")
    public ResponseEntity<List<UserDeliveryInfoView>> findUserDeliveryInfo(@RequestParam(required = false) String name,
                                                                           @RequestParam(required = false) String phone,
                                                                           @RequestParam(required = false) String zipCode) {
        log.info("Iniciando busca de informações de entrega: nome={}, telefone={}, cep={}", name, phone, zipCode);
        List<UserDeliveryInfoView> userDeliveryInfoViews = userDeliveryInfoServiceImpl.findUserDeliveryInfo(name, phone, zipCode);
        log.info("Busca de informações de entrega concluída com sucesso");
        return ResponseEntity.ok(userDeliveryInfoViews);
    }

}
