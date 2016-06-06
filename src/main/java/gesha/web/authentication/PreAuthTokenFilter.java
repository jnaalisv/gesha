package gesha.web.authentication;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Authenticates http requests with authorization set in Authorization-header
 *
 */
public class PreAuthTokenFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    public PreAuthTokenFilter(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return getAuthorizationHeaderValue(request);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return getAuthorizationHeaderValue(request);
    }

    private String getAuthorizationHeaderValue(HttpServletRequest request) {
        String authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        return authorizationHeaderValue != null && authorizationHeaderValue.startsWith(BEARER_PREFIX) ?
                authorizationHeaderValue.substring(BEARER_PREFIX.length()).trim() :
                authorizationHeaderValue;
    }
}

