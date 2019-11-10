package gesha.infrastructure.persistence;

import gesha.model.domain.user.User;
import gesha.model.domain.user.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HibernateUserRepository implements UserRepository {

    private final SessionFactory sessionFactory;

    public HibernateUserRepository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Optional<User> loadUserByUsername(String username) {
        return getCurrentSession()
                .createQuery("select user from User user where user.username = :username", User.class)
                .setParameter("username", username)
                .uniqueResultOptional();
    }

    @Override
    public void save(User user) {
        getCurrentSession().save(user);
    }

    @Override
    public List<User> getAll() {
        return getCurrentSession()
                .createQuery("SELECT user From User user", User.class)
                .list();
    }
}
