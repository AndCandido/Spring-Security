package io.github.AndCandido.apispringsecurity.security.authStrategy.authStrategies;

import io.github.AndCandido.apispringsecurity.exceptions.ApiAuthenticationException;
import io.github.AndCandido.apispringsecurity.exceptions.CredentialsInvalidException;
import io.github.AndCandido.apispringsecurity.security.authStrategy.AuthStrategyValidator;
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
    public UserDetails validate(String token) throws RuntimeException {
        UsernamePasswordAuthenticationToken authenticationToken = getCredentialsAuthToken(token);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if(authenticate == null || authenticate.getPrincipal() == null) {
            throw new CredentialsInvalidException("Authentication Failure");
        }

        return (UserDetails) authenticate.getPrincipal();
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
