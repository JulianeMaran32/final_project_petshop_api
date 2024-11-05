package br.com.juhmaran.petshop_api.api.auth.password.entities;

import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa um token de redefinição de senha.
 * <p>
 * Esta entidade é usada para armazenar tokens de redefinição de senha associados a usuários.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "password_reset_token")
@Schema(name = "PasswordResetToken", description = "Representa um token de redefinição de senha.")
public class PasswordResetTokenEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "BIGINT UNSIGNED",
            foreignKey = @ForeignKey(name = "FK_password_reset_token_user",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE"),
            referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "expiry_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime expiryDate;

    public PasswordResetTokenEntity(String token, UserEntity user) {
        this.token = token;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusHours(24);
    }

}