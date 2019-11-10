package gesha.web.interfaces.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import gesha.model.domain.user.User;
import gesha.web.authentication.UrlEncoderDecoder;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final Algorithm algorithm;

    public AuthenticationController(final AuthenticationManager authenticationManager, final Algorithm algorithm) {
        this.authenticationManager = authenticationManager;
        this.algorithm = algorithm;
    }

    @PostMapping(path = "authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String authenticate(@RequestBody CredentialsDTO credentials) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.username, credentials.password));

        User user = (User)authentication.getPrincipal();

        String authoritiesString = user
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        return JWT.create()
                .withIssuer("acme")
                .withClaim("sub", UrlEncoderDecoder.encode(user.getUsername()))
                .withClaim("scope", UrlEncoderDecoder.encode(authoritiesString))
                .withClaim("exp", System.currentTimeMillis() / 1000 + 10000)
                .sign(algorithm);
    }
}
