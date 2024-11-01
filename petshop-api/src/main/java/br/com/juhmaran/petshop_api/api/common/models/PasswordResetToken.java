package br.com.juhmaran.petshop_api.api.common.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PasswordResetToken {

    private Long id;
    private String token;
    private User user;
    private LocalDateTime expiryDate;
}
