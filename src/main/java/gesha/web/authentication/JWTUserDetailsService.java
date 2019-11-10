package gesha.web.authentication;

import com.auth0.jwt.JWTVerifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Map;

/**
 * This service is used to verify and extract user details from JSON Web Token that is stripped from the HTTP request by PreAuthTokenFilter.
 *
 */
public class JWTUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final JWTVerifier jwtVerifier;

    public JWTUserDetailsService(final JWTVerifier jwtVerifier) {
        this.jwtVerifier = jwtVerifier;
    }

    /**
     * Verify and extract UserDetails from JSON Web Token
     *
     * @param preAuthenticatedAuthentication
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthenticatedAuthentication) throws UsernameNotFoundException {

        String jwtToken = preAuthenticatedAuthentication.getCredentials().toString();

        Map<String, Object> jwtClaims = tryToVerify(jwtToken);

        return JWTUserDetails.fromJwtClaims(jwtClaims);
    }

    private Map<String, Object> tryToVerify(String jwtToken) {
        try {
            return jwtVerifier.verify(jwtToken);
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
}
