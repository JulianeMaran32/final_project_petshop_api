package br.com.juhmaran.petshop_api.api.user.services;

import br.com.juhmaran.petshop_api.api.user.dto.request.UserRegistrationRequest;
import br.com.juhmaran.petshop_api.api.user.dto.request.UserUpdateRequest;
import br.com.juhmaran.petshop_api.api.user.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse registerUser(UserRegistrationRequest userRegistrationRequest);

    UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);

    void deleteUser(Long id);

    void unlockAccount(String email);

    List<UserResponse> searchUsers(String name, String cpf, String email, String phone);

}
