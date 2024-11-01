package br.com.juhmaran.petshop_api.api.auth.password.repositories;

import br.com.juhmaran.petshop_api.api.auth.password.entities.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {

    Optional<PasswordResetTokenEntity> findByToken(String token);

    Optional<PasswordResetTokenEntity> findByUserEmail(String email);

}