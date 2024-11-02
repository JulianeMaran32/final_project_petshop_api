package br.com.juhmaran.petshop_api.core.security.services;

import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utilitário para manipulação de tokens JWT.
 * <p>
 * Esta classe fornece métodos para gerar, validar e extrair informações de tokens JWT.
 * </p>
 *
 * @author Juliane Maran
 */
@Slf4j
@Component
public class JwtUtil {

    @Value("${petshop.jwt.secret-key}")
    private String secret;

    @Value("${petshop.jwt.expiration}")
    private long expiration;

    /**
     * Extrai o nome de usuário do token JWT.
     *
     * @param token O token JWT
     * @return O nome de usuário extraído do token
     * @throws PetShopInternalServerErrorException Se ocorrer um erro ao extrair o nome de usuário
     */
    public String extractUsername(String token) {
        try {
            return getClaims(token).getSubject();
        } catch (Exception e) {
            log.error("Erro ao extrair nome de usuário do token.", e);
            throw new PetShopInternalServerErrorException("Erro ao extrair nome de usuário do token.", e);
        }
    }

    /**
     * Obtém o tempo de expiração do token JWT.
     *
     * @return O tempo de expiração do token em milissegundos
     * @throws PetShopInternalServerErrorException Se ocorrer um erro ao obter o tempo de expiração
     */
    public long getExpirationTime() {
        try {
            return expiration;
        } catch (Exception e) {
            log.error("Erro ao obter tempo de expiração do token.", e);
            throw new PetShopInternalServerErrorException("Erro ao obter tempo de expiração do token.", e);
        }
    }

    /**
     * Gera um token JWT para o usuário fornecido.
     *
     * @param userDetails Os detalhes do usuário
     * @return O token JWT gerado
     * @throws PetShopInternalServerErrorException Se ocorrer um erro ao gerar o token
     */
    public String getToken(UserDetails userDetails) {
        try {
            log.debug("Gerando token para o usuário: {}", userDetails.getUsername());
            Map<String, Object> claims = new HashMap<>();
            claims.put("email", userDetails.getUsername());
            claims.put("roles", userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).toList());
            return generateToken(claims, userDetails);
        } catch (Exception e) {
            log.error("Erro ao gerar token para o usuário: {}", userDetails.getUsername(), e);
            throw new PetShopInternalServerErrorException("Erro ao gerar token para o usuário.", e);
        }
    }

    /**
     * Gera um token JWT com claims adicionais.
     *
     * @param extraClaims Claims adicionais a serem incluídas no token
     * @param userDetails Os detalhes do usuário
     * @return O token JWT gerado
     * @throws PetShopInternalServerErrorException Se ocorrer um erro ao gerar o token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        try {
            return buildToken(extraClaims, userDetails, expiration);
        } catch (Exception e) {
            log.error("Erro ao gerar token com claims adicionais para o usuário: {}", userDetails.getUsername(), e);
            throw new PetShopInternalServerErrorException("Erro ao gerar token com claims adicionais.", e);
        }
    }

    /**
     * Valida o token JWT.
     *
     * @param token       O token JWT
     * @param userDetails Os detalhes do usuário
     * @return true se o token for válido, false caso contrário
     * @throws PetShopInternalServerErrorException Se ocorrer um erro ao validar o token
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            log.error("Erro ao validar token para o usuário: {}", userDetails.getUsername(), e);
            throw new PetShopInternalServerErrorException("Erro ao validar token.", e);
        }
    }

    /**
     * Constrói um token JWT com claims adicionais.
     *
     * @param extraClaims Claims adicionais a serem incluídas no token
     * @param userDetails Os detalhes do usuário
     * @param expiration  O tempo de expiração do token em milissegundos
     * @return O token JWT gerado
     * @throws PetShopInternalServerErrorException Se ocorrer um erro ao construir o token
     */
    private String buildToken(Map<String, Object> extraClaims,
                              UserDetails userDetails, long expiration) {
        try {
            var now = new Date();
            Date expirationDate = new Date(now.getTime() + expiration * 1000);

            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .claims(extraClaims)
                    .issuedAt(now)
                    .expiration(expirationDate)
                    .signWith(getSecretKey(), Jwts.SIG.HS256)
                    .compact();
        } catch (Exception e) {
            log.error("Erro ao construir token para o usuário: {}", userDetails.getUsername(), e);
            throw new PetShopInternalServerErrorException("Erro ao construir token.", e);
        }
    }

    /**
     * Verifica se o token JWT está expirado.
     *
     * @param token O token JWT
     * @return true se o token estiver expirado, false caso contrário
     * @throws PetShopInternalServerErrorException Se ocorrer um erro ao verificar a expiração do token
     */
    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            log.error("Erro ao verificar se o token está expirado.", e);
            throw new PetShopInternalServerErrorException("Erro ao verificar expiração do token.", e);
        }
    }

    /**
     * Extrai a data de expiração do token JWT.
     *
     * @param token O token JWT
     * @return A data de expiração do token
     * @throws PetShopInternalServerErrorException Se ocorrer um erro ao extrair a data de expiração
     */
    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            log.error("Erro ao extrair data de expiração do token.", e);
            throw new PetShopInternalServerErrorException("Erro ao extrair data de expiração do token.", e);
        }
    }

    /**
     * Extrai uma claim específica do token JWT.
     *
     * @param token          O token JWT
     * @param claimsResolver A função para resolver a claim
     * @param <T>            O tipo da claim
     * @return A claim extraída
     * @throws PetShopInternalServerErrorException Se ocorrer um erro ao extrair a claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = getClaims(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            log.error("Erro ao extrair claim do token.", e);
            throw new PetShopInternalServerErrorException("Erro ao extrair claim do token.", e);
        }
    }

    /**
     * Obtém as claims do token JWT.
     *
     * @param token O token JWT
     * @return As claims do token
     * @throws PetShopInternalServerErrorException Se ocorrer um erro ao obter as claims
     */
    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("Erro ao obter claims do token: {}", e.getMessage());
            throw new PetShopInternalServerErrorException("Erro ao obter claims do token");
        }
    }

    /**
     * Obtém a chave secreta usada para assinar o token JWT.
     *
     * @return A chave secreta
     * @throws PetShopInternalServerErrorException Se ocorrer um erro ao obter a chave secreta
     */
    private SecretKey getSecretKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            log.error("Erro ao obter chave secreta: {}", e.getMessage());
            throw new PetShopInternalServerErrorException("Erro ao obter chave secreta");
        }
    }

}
