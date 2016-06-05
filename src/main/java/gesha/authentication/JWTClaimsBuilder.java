package gesha.authentication;

import java.util.HashMap;
import java.util.Map;

public class JWTClaimsBuilder {

    public static final String USERNAME = "sub";
    public static final String EXPIRES = "exp";
    public static final String SCOPE = "scope";

    private Map<String, Object> jwtClaims;

    public JWTClaimsBuilder() {
        jwtClaims = new HashMap<>();
    }

    public JWTClaimsBuilder withExpiration(Long expiration) {
        jwtClaims.put(EXPIRES, expiration);
        return this;
    }

    public JWTClaimsBuilder withScope(String scope) {
        jwtClaims.put(SCOPE, UrlEncoderDecoder.encode(scope));
        return this;
    }

    public JWTClaimsBuilder withUsername(String username) {
        jwtClaims.put(USERNAME, UrlEncoderDecoder.encode(username));
        return this;
    }

    public Map<String, Object> build() {
        return this.jwtClaims;
    }
}
