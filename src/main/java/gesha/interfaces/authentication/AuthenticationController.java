package gesha.interfaces.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.Token;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final KeyBasedPersistenceTokenService keyBasedPersistenceTokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(final KeyBasedPersistenceTokenService keyBasedPersistenceTokenService, final AuthenticationManager authenticationManager) {
        this.keyBasedPersistenceTokenService = keyBasedPersistenceTokenService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(method = RequestMethod.POST, path = "authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String authenticate(@RequestBody CredentialsDTO credentials) throws Exception {

        UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(credentials.username, credentials.password);
        authenticationManager.authenticate(authenticationRequest);

        Token token = keyBasedPersistenceTokenService.allocateToken(credentials.username);
        return token.getKey();
    }
}
