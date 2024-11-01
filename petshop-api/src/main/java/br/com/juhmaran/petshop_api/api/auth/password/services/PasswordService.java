package br.com.juhmaran.petshop_api.api.auth.password.services;

public interface PasswordService {

    void resetPassword(String token, String newPassword, String confirmPassword);

    void createPasswordResetTokenForUser(String email);

    boolean validatePasswordResetToken(String token);

}
