package io.github.AndCandido.apispringsecurity.security.authStrategy.authStrategies;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.AndCandido.apispringsecurity.security.authStrategy.AuthStrategyValidator;
import io.github.AndCandido.apispringsecurity.security.authStrategy.AuthValidationResult;
import io.github.AndCandido.apispringsecurity.services.ITokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthValidator implements AuthStrategyValidator {
    public static final String STRATEGY_AUTH = "Bearer ";
    private final ITokenService tokenService;
    private final UserDetailsService userService;

    public JWTAuthValidator(ITokenService tokenService, UserDetailsService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public AuthValidationResult validate(String requestToken) {
        String token = getToken(requestToken);
        String tokenSubject;

        try {
            tokenSubject = tokenService.validateToken(token);
        } catch (JWTVerificationException ex) {
            return AuthValidationResult.failure("Invalid Token", HttpStatus.UNAUTHORIZED);
        }

        UserDetails user = userService.loadUserByUsername(tokenSubject);

        if(user == null) {
            return AuthValidationResult.failure("Token not authorized", HttpStatus.UNAUTHORIZED);
        }

        return AuthValidationResult.success(user);
    }

    private String getToken(String requestToken) {
        return requestToken.replace(STRATEGY_AUTH, "");
    }
}
