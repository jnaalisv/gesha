package gesha.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import gesha.authentication.PreAuthUserDetailsService;

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

    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthAuthenticationProvider(KeyBasedPersistenceTokenService keyBasedPersistenceTokenService) {
        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new PreAuthUserDetailsService(keyBasedPersistenceTokenService));
        return preAuthenticatedAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(PreAuthenticatedAuthenticationProvider preAuthProvider) {
        return new ProviderManager(Arrays.asList(preAuthProvider));
    }
}
