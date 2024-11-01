package br.com.juhmaran.petshop_api.api.auth.password.services.impl;

import br.com.juhmaran.petshop_api.api.auth.password.entities.PasswordResetTokenEntity;
import br.com.juhmaran.petshop_api.api.auth.password.repositories.PasswordResetTokenRepository;
import br.com.juhmaran.petshop_api.api.auth.password.services.PasswordService;
import br.com.juhmaran.petshop_api.api.emails.services.EmailService;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.api.user.repositories.UserRepository;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopConflictException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopNotFoundException;
import br.com.juhmaran.petshop_api.core.validator.annotations.ValidPassword;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;

    private static final SecureRandom random = new SecureRandom();

    @Transactional
    @Override
    public void createPasswordResetTokenForUser(@Email(message = "Email inválido.") String email) {
        UserEntity user = getUserEntity(email);
        String token = String.format("%06d", random.nextInt(999999));
        var passwordResetToken = new PasswordResetTokenEntity(token, user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        tokenRepository.save(passwordResetToken);
        emailService.sendPasswordResetToken(email, token);
    }

    @Override
    public boolean validatePasswordResetToken(String token) {
        PasswordResetTokenEntity passwordResetToken = getPasswordResetTokenEntity(token);
        return passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now());
    }

    @Transactional
    @Override
    public void resetPassword(String token, @ValidPassword String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new PetShopConflictException("A nova senha e a confirmação da senha não correspondem.");
        }
        PasswordResetTokenEntity passwordResetToken = getPasswordResetTokenEntity(token);
        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new PetShopConflictException("Token inválido ou expirado.");
        }
        UserEntity user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(passwordResetToken);
    }

    private PasswordResetTokenEntity getPasswordResetTokenEntity(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(() -> new PetShopConflictException("Token inválido ou expirado."));
    }

    private UserEntity getUserEntity(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new PetShopNotFoundException("Email não encontrado."));
    }

}
