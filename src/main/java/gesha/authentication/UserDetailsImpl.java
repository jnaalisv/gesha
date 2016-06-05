package gesha.authentication;

import static gesha.authentication.JWTClaimsBuilder.USERNAME;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

    private final String username;

    public UserDetailsImpl(final String username) {
        this.username = username;
    }

    public static UserDetailsImpl fromJwtClaims(final Map<String, Object> claims) {
        return new UserDetailsImpl(UrlEncoderDecoder.decode(claims.get(USERNAME)));
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
