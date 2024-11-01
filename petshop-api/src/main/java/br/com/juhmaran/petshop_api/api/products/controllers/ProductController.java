package br.com.juhmaran.petshop_api.api.products.controllers;

import br.com.juhmaran.petshop_api.api.products.dtos.ProductRequest;
import br.com.juhmaran.petshop_api.api.products.dtos.ProductResponse;
import br.com.juhmaran.petshop_api.api.products.dtos.ProductUpdateRequest;
import br.com.juhmaran.petshop_api.api.products.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Operações relacionadas a produtos")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Listar Produtos.", description = "Retorna uma lista de produtos cadastrados no banco de dados.",
            tags = {"Product"}, operationId = "findAll", responses = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de produtos", ref = "ProductResponse"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", ref = "ErrorResponse")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @Operation(summary = "Buscar Produto.", description = "Busca um produto no banco de dados.", tags = {"Product"},
            operationId = "findById", responses = {
            @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso.", ref = "ProductResponse"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", ref = "ErrorResponse")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        ProductResponse productEntity = productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productEntity);
    }

    @Operation(summary = "Cadastrar Produto.", description = "Cadastra um novo produto no banco de dados.", tags = {"Product"},
            operationId = "createProduct", security = @SecurityRequirement(name = "bearer"), responses = {
            @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso.", ref = "ProductResponse"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição.", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão.", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "409", description = "Produto já cadastrado.", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", ref = "ErrorResponse")
    })
    @PreAuthorize("anyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productEntity));
    }

    @Operation(summary = "Atualizar Produto.", description = "Atualiza um produto no banco de dados.", tags = {"Product"},
            operationId = "updateProduct", security = @SecurityRequirement(name = "bearer"), responses = {
            @ApiResponse(responseCode = "200", description = "Dado(s) do Produto, atualizado(s) com sucesso.",
                    ref = "ProductResponse"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição.", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão.", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", ref = "ErrorResponse")
    })
    @PreAuthorize("anyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @RequestBody ProductUpdateRequest updateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, updateRequest));
    }

    @Operation(summary = "Deleta um produto.", description = "Deleta um produto do banco de dados.", tags = {"Product"},
            operationId = "deleteProduct", security = @SecurityRequirement(name = "bearer"), responses = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro na requisição.", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão.", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", ref = "ErrorResponse"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", ref = "ErrorResponse")
    })
    @PreAuthorize("anyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Buscar Produtos.", description = "Busca produtos no banco de dados com base em parâmetros opcionais.",
            tags = {"Product"}, operationId = "searchProducts", responses = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de produtos", ref = "ProductResponse"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", ref = "ErrorResponse")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String supplier,
            @RequestParam(required = false) String barcode) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProducts(name, category, supplier, barcode));
    }

}
