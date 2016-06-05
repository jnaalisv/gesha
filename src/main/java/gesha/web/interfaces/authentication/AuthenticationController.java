package gesha.web.interfaces.authentication;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWTSigner;

import gesha.model.domain.user.User;
import gesha.web.authentication.JWTClaimsBuilder;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTSigner jwtSigner;

    @Autowired
    public AuthenticationController(final AuthenticationManager authenticationManager, final JWTSigner jwtSigner) {
        this.authenticationManager = authenticationManager;
        this.jwtSigner = jwtSigner;
    }

    @RequestMapping(method = RequestMethod.POST, path = "authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String authenticate(@RequestBody CredentialsDTO credentials) throws Exception {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.username, credentials.password));

        User user = (User)authentication.getPrincipal();

        String authoritiesString = user
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Map<String, Object> jwtClaims = new JWTClaimsBuilder()
                .withScope(authoritiesString)
                .withUsername(user.getUsername())
                .withExpiration((System.currentTimeMillis() / 1000) + 10000)
                .build();

        return jwtSigner.sign(jwtClaims);
    }
}
