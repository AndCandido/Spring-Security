package io.github.AndCandido.apispringsecurity.security;

import io.github.AndCandido.apispringsecurity.security.authStrategy.AuthStrategyManager;
import io.github.AndCandido.apispringsecurity.security.authStrategy.AuthStrategyValidator;
import io.github.AndCandido.apispringsecurity.security.authStrategy.AuthValidationResult;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AppSecurityFilter extends OncePerRequestFilter {

    private final AuthStrategyManager authStrategyManager;

    public AppSecurityFilter(AuthStrategyManager authStrategyManager) {
        this.authStrategyManager = authStrategyManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = getAuth(request);

        if(auth == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String authStrategy = getAuthStrategy(auth);
        AuthStrategyValidator authStrategyValidator = authStrategyManager.getAuthStrategyByName(authStrategy);
        AuthValidationResult validationResult = authStrategyValidator.validate(auth);

        if(!validationResult.isValid()) {
            response.getWriter().write(validationResult.messageError());
            response.setStatus(validationResult.statusError().value());
            return;
        }

        authorizeUser(validationResult.user());
        filterChain.doFilter(request, response);
    }

    private String getAuth(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private String getAuthStrategy(String auth) {
        return auth.split(" ")[0];
    }

    private void authorizeUser(UserDetails user) {
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
