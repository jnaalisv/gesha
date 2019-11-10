package gesha.web.authentication;

import com.auth0.jwt.interfaces.Claim;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class JWTUserDetails implements UserDetails {
    private static final String USERNAME = "sub";

    private final String username;

    public JWTUserDetails(final String username) {
        this.username = username;
    }

    public static JWTUserDetails fromJwtClaims(final Map<String, Claim> claims) {
        return new JWTUserDetails(UrlEncoderDecoder.decode(claims.get(USERNAME).asString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList((GrantedAuthority) () -> username);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
