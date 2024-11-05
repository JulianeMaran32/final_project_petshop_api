package br.com.juhmaran.petshop_api.api.pet.controllers;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.api.common.enums.Species;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetRequest;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetResponse;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetStatisticsResponse;
import br.com.juhmaran.petshop_api.api.pet.services.PetService;
import br.com.juhmaran.petshop_api.api.pet.services.PetStatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
@Tag(name = "Pets", description = "Operações relacionadas a Pets")
public class PetController {

    private final PetStatisticsService petStatisticsService;
    private final PetService petService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> findById(@PathVariable(name = "id") Long id) {
        log.debug("Recebida solicitação para encontrar pet com ID: {}", id);
        PetResponse petResponse = petService.findById(id);
        log.info("Pet com ID: {} encontrado com sucesso", id);
        return ResponseEntity.status(HttpStatus.OK).body(petResponse);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/filter")
    public ResponseEntity<List<PetResponse>> filterPets(@RequestParam(required = false) Long id,
                                                        @RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String breed,
                                                        @RequestParam(required = false) Species species,
                                                        @RequestParam(required = false) Gender gender,
                                                        @RequestParam(required = false) LocalDateTime createdDate) {
        List<PetResponse> pets = petService.filterPets(id, name, breed, species, gender, createdDate);
        return ResponseEntity.status(HttpStatus.OK).body(pets);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<PetResponse> createPet(@Valid @RequestBody PetRequest petRequest,
                                                 Authentication authentication) {
        PetResponse petResponse = petService.createPet(petRequest, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(petResponse);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{petId}")
    public ResponseEntity<PetResponse> updatePet(@PathVariable Long petId,
                                                 @Valid @RequestBody PetRequest petRequest,
                                                 Authentication authentication) {
        PetResponse petResponse = petService.updatePet(petId, petRequest, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(petResponse);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable Long petId, Authentication authentication) {
        petService.deletePet(petId, authentication.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<PetResponse> createPetForCustomer(@Valid @RequestBody PetRequest petRequest) {
        PetResponse petResponse = petService.createPetForCustomer(petRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(petResponse);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("/admin/{petId}")
    public ResponseEntity<PetResponse> updatePetForCustomer(@PathVariable Long petId,
                                                            @Valid @RequestBody PetRequest petRequest) {
        PetResponse petResponse = petService.updatePetForCustomer(petId, petRequest);
        return ResponseEntity.status(HttpStatus.OK).body(petResponse);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping("/admin/{petId}")
    public ResponseEntity<Void> deletePetForCustomer(@PathVariable Long petId) {
        petService.deletePetForCustomer(petId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("/statistics")
    public List<PetStatisticsResponse> getPetStatistics() {
        return petStatisticsService.getPetStatistics();
    }

}
