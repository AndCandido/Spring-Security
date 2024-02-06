package io.github.AndCandido.apispringsecurity.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.AndCandido.apispringsecurity.services.ITokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenServiceImpl implements ITokenService {

    @Value("${api.auth.jwt.issuer}")
    private String issuer;

    @Value("${api.auth.jwt.secret-key}")
    private String secret;

    @Value("${api.auth.jwt.days-token-expiration}")
    private int daysTokenExpiration;

    @Override
    public String generateToken(UserDetails user) throws JWTCreationException {
        return JWT.create()
            .withSubject(user.getUsername())
            .withIssuer(issuer)
            .withIssuedAt(new Date(System.currentTimeMillis()))
            .withExpiresAt(getExpirationInstant())
            .withJWTId(UUID.randomUUID().toString())
            .sign(Algorithm.HMAC256(secret));
    }

    @Override
    public DecodedJWT validateToken(String token) throws JWTVerificationException {
        return JWT
            .require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .build()
            .verify(token);
    }

    private Instant getExpirationInstant() {
        return LocalDateTime.now().plusDays(daysTokenExpiration).toInstant(ZoneOffset.of("-03:00"));
    }
}
