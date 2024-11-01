package br.com.juhmaran.petshop_api.api.contact.controllers;

import br.com.juhmaran.petshop_api.api.contact.dtos.ContactFormRequest;
import br.com.juhmaran.petshop_api.api.contact.services.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Contact", description = "Operações relacionadas a contato")
public class ContactController {

    private final ContactService contactService;

    @Operation(summary = "Envio de Email para Contato", operationId = "sendContactEmail", tags = {"Contact"},
            description = "Usuário envia um e-mail de contato para o suporte.")
    @ApiResponse(responseCode = "200", description = "E-mail enviado com sucesso!")
    @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro ao enviar o e-mail.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping("/contacts")
    public ResponseEntity<Void> sendContactEmail(@Valid @RequestBody ContactFormRequest contactForm) {
        contactService.sendContactEmail(contactForm);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
