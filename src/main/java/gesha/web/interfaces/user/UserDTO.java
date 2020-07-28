package gesha.web.interfaces.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gesha.domain.User;

public class UserDTO {

    public long ID;
    public String username;
    public String password;

    @JsonCreator
    public UserDTO(@JsonProperty("ID") long ID, @JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.ID = ID;
        this.username = username;
        this.password = password;
    }

    public UserDTO(User user) {
        this(user.getID(), user.getUsername(), null);
    }
}
