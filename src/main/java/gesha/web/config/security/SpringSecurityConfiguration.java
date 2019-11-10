package gesha.web.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import gesha.model.config.DomainConfiguration;
import gesha.web.authentication.JWTUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.Arrays;

@Import(DomainConfiguration.class)
public class SpringSecurityConfiguration {

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256("secret");
    }

    @Bean
    public JWTVerifier jwtVerifier(Algorithm algorithm) {
        return JWT.require(algorithm)
                .withIssuer("acme")
                .build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthAuthenticationProvider(JWTVerifier jwtVerifier) {
        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new JWTUserDetailsService(jwtVerifier));
        return preAuthenticatedAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(PreAuthenticatedAuthenticationProvider jwtAuthProvider, DaoAuthenticationProvider daoAuthenticationProvider) {
        return new ProviderManager(Arrays.asList(jwtAuthProvider, daoAuthenticationProvider));
    }
}
