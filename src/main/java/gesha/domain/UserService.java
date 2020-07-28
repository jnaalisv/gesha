package gesha.domain;

import java.util.List;

public interface UserService {

    void saveNew(User user);

    List<User> getAll();
}
