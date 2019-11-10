package gesha.model.application;

import gesha.model.domain.user.User;

import java.util.List;

public interface UserService {

    void saveNew(User user);

    List<User> getAll();
}
