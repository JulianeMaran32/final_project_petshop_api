package br.com.juhmaran.petshop_api.api.auth.login.services;

import br.com.juhmaran.petshop_api.api.auth.login.dtos.LoginRequest;
import br.com.juhmaran.petshop_api.api.auth.login.dtos.TokenResponse;
import br.com.juhmaran.petshop_api.api.user.dto.response.UserResponse;

/**
 * @author Juliane Maran
 */
public interface AuthService {

    /**
     * @param loginRequest LoginRequest
     * @return TokenResponse
     */
    TokenResponse login(LoginRequest loginRequest);

    /**
     * @return UserResponse
     */
    UserResponse getCurrentUser();

}
