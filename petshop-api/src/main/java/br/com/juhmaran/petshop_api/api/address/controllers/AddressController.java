package br.com.juhmaran.petshop_api.api.address.controllers;

import br.com.juhmaran.petshop_api.api.address.dtos.AddressRequest;
import br.com.juhmaran.petshop_api.api.address.dtos.AddressResponse;
import br.com.juhmaran.petshop_api.api.address.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Tag(name = "Address", description = "Endpoints para gerenciamento de endereços")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Obter endereço por CEP", operationId = "getAddressByCep", tags = {"Address"},
            description = "Recuperar detalhes do endereço pelo CEP")
    @ApiResponse(responseCode = "200", description = "Endereço encontrado",
            content = @Content(schema = @Schema(implementation = AddressResponse.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/cep/{cep}")
    public ResponseEntity<AddressResponse> getAddressByCep(@PathVariable(value = "cep") String cep) {
        AddressResponse addressResponseDTO = addressService.getAddressByCep(cep);
        return ResponseEntity.ok(addressResponseDTO);
    }

    @Operation(summary = "Buscar endereços", operationId = "getAddressByDetails", tags = {"Address"},
            description = "Buscar endereços por UF, cidade e rua")
    @ApiResponse(responseCode = "200", description = "Endereços encontrados",
            content = @Content(schema = @Schema(implementation = AddressResponse.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/search")
    public ResponseEntity<List<AddressResponse>> getAddressByDetails(@RequestParam(value = "uf") String uf,
                                                                     @RequestParam(value = "city") String city,
                                                                     @RequestParam(value = "street") String street) {
        List<AddressResponse> addressResponseDTOs = addressService.getAddressByDetails(uf, city, street);
        return ResponseEntity.ok(addressResponseDTOs);
    }

    @Operation(summary = "Criar um novo endereço", operationId = "createAddress", tags = {"Address"},
            description = "Criar uma nova entrada de endereço", security = {@SecurityRequirement(name = "bearer")})
    @ApiResponse(responseCode = "201", description = "Endereço criado",
            content = @Content(schema = @Schema(implementation = AddressResponse.class)))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Não autenticado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest addressRequestDTO) {
        AddressResponse addressResponseDTO = addressService.createAddress(addressRequestDTO);
        return ResponseEntity.ok(addressResponseDTO);
    }

    @Operation(summary = "Atualizar um endereço", operationId = "updateAddress", tags = {"Address"},
            description = "Atualizar um endereço existente pelo ID", security = {@SecurityRequirement(name = "bearer")})
    @ApiResponse(responseCode = "200", description = "Endereço atualizado",
            content = @Content(schema = @Schema(implementation = AddressResponse.class)))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Não autenticado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable(value = "id") Long id,
                                                         @RequestBody AddressRequest addressRequestDTO) {
        AddressResponse addressResponseDTO = addressService.updateAddress(id, addressRequestDTO);
        return ResponseEntity.ok(addressResponseDTO);
    }

    @Operation(summary = "Excluir um endereço", operationId = "deleteAddress", tags = {"Address"},
            description = "Excluir um endereço pelo ID", security = {@SecurityRequirement(name = "bearer")})
    @ApiResponse(responseCode = "204", description = "Endereço excluído")
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Não autenticado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable(value = "id") Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

}
