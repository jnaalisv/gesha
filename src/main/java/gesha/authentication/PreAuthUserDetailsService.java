package gesha.authentication;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class PreAuthUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final KeyBasedPersistenceTokenService keyBasedPersistenceTokenService;

    public PreAuthUserDetailsService(final KeyBasedPersistenceTokenService keyBasedPersistenceTokenService) {
        this.keyBasedPersistenceTokenService = keyBasedPersistenceTokenService;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthenticatedAuthentication) throws UsernameNotFoundException {

        // PreAuthTokenFilter populates the preAuthenticatedAuthentication
        String tokenKey = preAuthenticatedAuthentication.getCredentials().toString();

        Token verifiedToken = tryToVerify(tokenKey);

        // when generating the token, username is presented as extended information
        return new PreAuthUserDetails(verifiedToken.getExtendedInformation());
    }

    private Token tryToVerify(String tokenKey) {
        try {
            return keyBasedPersistenceTokenService.verifyToken(tokenKey);
        } catch (Exception e) {
            throw new BadCredentialsException("Token verification failed");
        }
    }
}
