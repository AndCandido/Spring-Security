package io.github.AndCandido.apispringsecurity.security.authStrategy;

import io.github.AndCandido.apispringsecurity.security.authStrategy.authStrategies.BasicAuthValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AuthStrategyManager {

    private final ApplicationContext applicationContext;

    public AuthStrategyManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public AuthStrategyValidator getAuthStrategyByName(String authStrategy) {
        AuthStrategyEnum authStrategyEnum =
            AuthStrategyEnum.getAuthStrategyByName(authStrategy);

        return authStrategyEnum.getImpl(applicationContext);
    }

    enum AuthStrategyEnum {
        BASIC("Basic", BasicAuthValidator.class);

        private final String name;
        private final Class<? extends AuthStrategyValidator> authStrategyValidator;

        AuthStrategyEnum(String name, Class<? extends AuthStrategyValidator> authStrategyValidator) {
            this.name = name;
            this.authStrategyValidator = authStrategyValidator;
        }

        public static AuthStrategyEnum getAuthStrategyByName(String name) {
            for (AuthStrategyEnum authStrategy : values()) {
                if(authStrategy.name.equals(name)) {
                    return authStrategy;
                }
            }

            throw new RuntimeException("Authorization Strategy Not Supported");
        }

        AuthStrategyValidator getImpl(ApplicationContext context) {
            return context.getBean(authStrategyValidator);
        }
    }
}
