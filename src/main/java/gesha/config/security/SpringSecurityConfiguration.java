package gesha.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public KeyBasedPersistenceTokenService keyBasedPersistenceTokenService() throws Exception {
        KeyBasedPersistenceTokenService keyBasedPersistenceTokenService = new KeyBasedPersistenceTokenService();
        keyBasedPersistenceTokenService.setSecureRandom(new SecureRandomFactoryBean().getObject());
        keyBasedPersistenceTokenService.setServerInteger(981463275);
        keyBasedPersistenceTokenService.setServerSecret("98sd98fyasdjfh23082n");
        return keyBasedPersistenceTokenService;
    }
}
