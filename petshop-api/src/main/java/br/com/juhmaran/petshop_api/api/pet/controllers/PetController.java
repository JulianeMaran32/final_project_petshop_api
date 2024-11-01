package br.com.juhmaran.petshop_api.api.pet.controllers;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.api.common.enums.Species;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetRequest;
import br.com.juhmaran.petshop_api.api.pet.dtos.PetResponse;
import br.com.juhmaran.petshop_api.api.pet.services.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<PetResponse>> getPets(@RequestParam(required = false) String name,
                                                     @RequestParam(required = false) Boolean castrated,
                                                     @RequestParam(required = false) Species species,
                                                     @RequestParam(required = false) Gender gender) {
        List<PetResponse> pets = petService.getPets(name, castrated, species, gender);
        return ResponseEntity.ok(pets);
    }

    @PostMapping("/admin")
    public ResponseEntity<PetResponse> createPetForUser(@RequestParam(name = "user_id") Long userId,
                                                        @RequestBody @Valid PetRequest petRequest) {
        PetResponse petResponse = petService.createPetForUser(userId, petRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(petResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<PetResponse> createPet(@RequestBody @Valid PetRequest petRequest,
                                                 Authentication authentication) {
        PetResponse petResponse = petService.createPet(petRequest, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(petResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> getPetById(@PathVariable(name = "id") Long id) {
        PetResponse petResponse = petService.getPetById(id);
        return ResponseEntity.ok(petResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> updatePetById(@PathVariable(name = "id") Long id,
                                                     @RequestBody @Valid PetRequest petRequest) {
        PetResponse petResponse = petService.updatePetById(id, petRequest);
        return ResponseEntity.ok(petResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PetResponse> patchPetById(@PathVariable(name = "id") Long id,
                                                    @RequestBody @Valid PetRequest petRequest) {
        PetResponse petResponse = petService.patchPetById(id, petRequest);
        return ResponseEntity.ok(petResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@PathVariable(name = "id") Long id) {
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<PetResponse>> getUserPets(@RequestParam(required = false) String name) {
        List<PetResponse> pets = petService.getUserPets(name);
        return ResponseEntity.ok(pets);
    }

}
