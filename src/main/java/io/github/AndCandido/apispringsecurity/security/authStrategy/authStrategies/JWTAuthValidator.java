package io.github.AndCandido.apispringsecurity.security.authStrategy.authStrategies;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.AndCandido.apispringsecurity.exceptions.TokenNotAuthorized;
import io.github.AndCandido.apispringsecurity.security.authStrategy.AuthStrategyValidator;
import io.github.AndCandido.apispringsecurity.services.ITokenService;
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
    public UserDetails validate(String requestToken) throws RuntimeException {
        String token = getToken(requestToken);

        DecodedJWT decodedToken = tokenService.validateToken(token);
        String tokenSubject = decodedToken.getSubject();

        UserDetails user = userService.loadUserByUsername(tokenSubject);

        if(user == null) {
            throw new TokenNotAuthorized("Token not authorized");
        }

        return user;
    }

    private String getToken(String requestToken) {
        return requestToken.replace(STRATEGY_AUTH, "");
    }
}
