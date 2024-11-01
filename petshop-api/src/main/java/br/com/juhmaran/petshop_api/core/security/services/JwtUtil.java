package br.com.juhmaran.petshop_api.core.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${petshop.jwt.secret-key}")
    private String secret;

    @Value("${petshop.jwt.expiration}")
    private long expiration;

    public String generateToken(String username) {
        log.info("Gerando token para o usuário: {}", username);
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
        log.info("Token gerado com sucesso para o usuário: {}", username);
        return token;
    }

    public String getUsernameFromToken(String token) {
        log.info("Extraindo nome de usuário do token.");
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();
        log.info("Nome de usuário extraído do token: {}", username);
        return username;
    }

    public boolean validateToken(String token) {
        log.info("Validando token.");
        boolean isValid = getUsernameFromToken(token) != null && !isTokenExpired(token);
        log.info("Token {} é válido.", isValid ? "" : "não");
        return isValid;
    }

    private boolean isTokenExpired(String token) {
        log.info("Verificando se o token está expirado.");
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        boolean isExpired = claims.getExpiration().before(new Date());
        log.info("Token {} está expirado.", isExpired ? "" : "não");
        return isExpired;
    }

    public long getExpirationTime() {
        return expiration;
    }

}
