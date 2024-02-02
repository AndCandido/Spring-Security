package io.github.AndCandido.apispringsecurity.services;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.core.userdetails.UserDetails;

public interface ITokenService {
    String generateToken(UserDetails user) throws JWTCreationException;

    String validateToken(String token) throws JWTVerificationException;
}
