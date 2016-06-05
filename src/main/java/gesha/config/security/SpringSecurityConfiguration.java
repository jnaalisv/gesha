package gesha.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;

import gesha.authentication.JWTUserDetailsService;
import gesha.authentication.UserNameEqualsPasswordAuthProvider;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public JWTSigner jwtSigner() {
        return new JWTSigner("secret");
    }

    @Bean
    public JWTVerifier jwtVerifier() {
        return new JWTVerifier("secret");
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthAuthenticationProvider(JWTVerifier jwtVerifier) {
        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new JWTUserDetailsService(jwtVerifier));
        return preAuthenticatedAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(PreAuthenticatedAuthenticationProvider jwtAuthProvider) {
        return new ProviderManager(Arrays.asList(jwtAuthProvider, new UserNameEqualsPasswordAuthProvider()));
    }
}
