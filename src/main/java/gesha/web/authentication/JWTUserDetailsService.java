package gesha.web.authentication;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

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

        DecodedJWT decodedJWT = tryToVerify(jwtToken);

        return JWTUserDetails.fromJwtClaims(decodedJWT.getClaims());
    }

    private DecodedJWT tryToVerify(String jwtToken) {
        try {
            return jwtVerifier.verify(jwtToken);
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
}
