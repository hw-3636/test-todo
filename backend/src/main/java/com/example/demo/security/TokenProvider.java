package com.example.demo.security;

import com.example.demo.model.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String create(UserEntity userEntity) {
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .setSubject(String.valueOf(userEntity.getId()))
                .setIssuer("demo app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        log.info("Decoded Secret Key: {}", new String(secretKey.getBytes(StandardCharsets.UTF_8)));
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            log.info("getSubject: {}", claims.getSubject());
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            log.error("JWT token has expired: {}", token);
            throw new RuntimeException("Token has expired", e);  // 예외 처리
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", token);
            throw new RuntimeException("Invalid signature", e);  // 예외 처리
        } catch (JwtException e) {
            log.error("Invalid JWT token: {}", token);
            throw new RuntimeException("Invalid token", e);  // 예외 처리
        }

    }
}
