package gesha.model.domain.user;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long ID;

    private String username;
    private String password;

    public User() { /* for hibernate*/}

    public User(final String username, String password) {
        this.username = username;
        this.password = password;
    }

//    public static GeshaUser fromJwtClaims(final Map<String, Object> claims) {
//        return new GeshaUser(UrlEncoderDecoder.decode(claims.get(USERNAME)));
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList((GrantedAuthority) () -> username);
    }

    @Override
    public String getPassword() {
        return password;
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

    public long getID() {
        return ID;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
