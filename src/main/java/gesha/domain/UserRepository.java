package gesha.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> loadUserByUsername(String username);

    void save(User user);

    List<User> getAll();

}
