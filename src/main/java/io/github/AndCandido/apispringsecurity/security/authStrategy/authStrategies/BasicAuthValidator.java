package io.github.AndCandido.apispringsecurity.security.authStrategy.authStrategies;

import io.github.AndCandido.apispringsecurity.security.authStrategy.AuthStrategyValidator;
import io.github.AndCandido.apispringsecurity.security.authStrategy.AuthValidationResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class BasicAuthValidator implements AuthStrategyValidator {
    private static final String BASIC_AUTH = "Basic ";
    private final UserDetailsService userService;
    private final PasswordEncoder passwordEncoder;

    public BasicAuthValidator(UserDetailsService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthValidationResult validate(String token) {
        String basicAuth = token.replace(BASIC_AUTH, "");
        String basicAuthDecoded = decodeBase64(basicAuth);
        String[] credentials = basicAuthDecoded.split(":");

        String username = credentials[0];
        String password = credentials[1];

        UserDetails user = userService.loadUserByUsername(username);

        if(user == null) {
            return AuthValidationResult.failure("User Not Exists", HttpStatus.UNAUTHORIZED);
        }

        if(!isValidatePassword(password, user.getPassword())) {
            return AuthValidationResult.failure("Incorrect Password", HttpStatus.UNAUTHORIZED);
        }

        return AuthValidationResult.success(user);
    }

    private String decodeBase64(String stringToDecode) {
        byte[] decoded = Base64.getDecoder().decode(stringToDecode);
        return new String(decoded);
    }

    private boolean isValidatePassword(String providedPassword, String userPassword) {
        return passwordEncoder.matches(providedPassword, userPassword);
    }
}
