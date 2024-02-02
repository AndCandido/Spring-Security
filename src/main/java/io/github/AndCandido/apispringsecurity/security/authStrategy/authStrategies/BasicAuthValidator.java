package io.github.AndCandido.apispringsecurity.security.authStrategy.authStrategies;

import io.github.AndCandido.apispringsecurity.exceptions.CredentialsInvalidException;
import io.github.AndCandido.apispringsecurity.security.authStrategy.AuthStrategyValidator;
import io.github.AndCandido.apispringsecurity.security.authStrategy.AuthValidationResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Objects;

@Component
public class BasicAuthValidator implements AuthStrategyValidator {

    public static final String STRATEGY_AUTH = "Basic ";
    private final AuthenticationManager authenticationManager;

    public BasicAuthValidator(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthValidationResult validate(String token) {
        Authentication authenticate;

        try {
            UsernamePasswordAuthenticationToken authenticationToken = getCredentialsAuthToken(token);
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException ex) {
            return AuthValidationResult.failure("Username or password is incorrect", HttpStatus.UNAUTHORIZED);
        } catch (CredentialsInvalidException ex) {
            return AuthValidationResult.failure(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        if(authenticate == null) {
            return AuthValidationResult.failure("Authentication Failure", HttpStatus.UNAUTHORIZED);
        }

        return AuthValidationResult.success((UserDetails) authenticate.getPrincipal());
    }

    private UsernamePasswordAuthenticationToken getCredentialsAuthToken(String token) {
        String basicAuth = token.replace(STRATEGY_AUTH, "");
        String basicAuthDecoded = decodeBase64(basicAuth);
        String[] credentials = basicAuthDecoded.split(":");

        if(credentials.length < 2 || Objects.equals(credentials[0], "")) {
            throw new CredentialsInvalidException("Credentials invalid");
        }

        String username = credentials[0];
        String password = credentials[1];

        return new UsernamePasswordAuthenticationToken(username, password);
    }

    private String decodeBase64(String stringToDecode) {
        byte[] decoded = Base64.getDecoder().decode(stringToDecode);
        return new String(decoded);
    }
}
