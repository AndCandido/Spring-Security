package io.github.AndCandido.apispringsecurity.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService implements ITokenService {

    @Value("${api.auth.jwt.issuer}")
    private String issuer;

    @Value("${api.auth.jwt.secret-token}")
    private String secret;

    @Value("${api.auth.jwt.days-token-expiration}")
    private int daysTokenExpiration;

    @Override
    public String generateToken(UserDetails user) throws JWTCreationException {
        return JWT.create()
            .withSubject(user.getUsername())
            .withIssuer(issuer)
            .withExpiresAt(getExpirationInstant())
            .sign(Algorithm.HMAC256(secret));
    }

    @Override
    public String validateToken(String token) throws JWTVerificationException {
        return JWT
            .require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .build()
            .verify(token)
            .getSubject();
    }

    private Instant getExpirationInstant() {
        return LocalDateTime.now().plusDays(daysTokenExpiration).toInstant(ZoneOffset.of("-03:00"));
    }
}
