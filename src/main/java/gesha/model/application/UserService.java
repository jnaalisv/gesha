package gesha.model.application;

import java.util.List;

import gesha.model.domain.user.User;

public interface UserService {

    void saveNew(User user);

    List<User> getAll();
}
