package gesha.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import gesha.model.domain.user.User;
import gesha.model.domain.user.UserRepository;

@Repository
public class HibernateUserRepository implements UserRepository {

    private final SessionFactory sessionFactory;

    @Inject
    public HibernateUserRepository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Optional<User> loadUserByUsername(String username) {
        return Optional.ofNullable ((User) getCurrentSession()
                .createQuery("SELECT user From User user where user.username = :username")
                .setParameter("username", username)
                .uniqueResult());
    }

    @Override
    public void save(User user) {
        getCurrentSession().save(user);
    }

    @Override
    public List<User> getAll() {
        return getCurrentSession()
                .createQuery("SELECT user From User user")
                .list();
    }
}