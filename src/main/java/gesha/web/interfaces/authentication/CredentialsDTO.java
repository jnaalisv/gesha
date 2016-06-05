package gesha.web.interfaces.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CredentialsDTO {

    public final String username;
    public final String password;

    @JsonCreator
    public CredentialsDTO (
            @JsonProperty("username") final String username,
            @JsonProperty("password") final String password
    ) {
        this.username = username;
        this.password = password;
    }
}
