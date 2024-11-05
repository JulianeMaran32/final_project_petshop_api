package br.com.juhmaran.petshop_api.api.user.controllers;

import br.com.juhmaran.petshop_api.api.user.dto.request.UserRegistrationRequest;
import br.com.juhmaran.petshop_api.api.user.dto.request.UserUpdateRequest;
import br.com.juhmaran.petshop_api.api.user.dto.response.UserResponse;
import br.com.juhmaran.petshop_api.api.user.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operações relacionadas ao Usuário")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        log.info("Criando usuário: {}", userRegistrationRequest);
        UserResponse userResponse = userService.registerUser(userRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> updateUser(@PathVariable(value = "id") Long id,
                                                   @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        log.info("Atualizando usuário: {}", userUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, userUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id) {
        log.info("Deletando usuário com id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/unlock")
    public ResponseEntity<String> unlockAccount(@RequestParam(value = "email") String email) {
        userService.unlockAccount(email);
        return ResponseEntity.status(HttpStatus.OK).body("Conta desbloqueada com sucesso");
    }

    @GetMapping("/users/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String cpf,
                                                          @RequestParam(required = false) String email,
                                                          @RequestParam(required = false) String phone) {
        List<UserResponse> userResponses = userService.searchUsers(name, cpf, email, phone);
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

}
