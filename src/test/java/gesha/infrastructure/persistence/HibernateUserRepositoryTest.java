package gesha.infrastructure.persistence;

import gesha.infrastructure.config.PersistenceConfiguration;
import gesha.model.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(classes = {PersistenceConfiguration.class})
class HibernateUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void getAllFindsAllUsers() {
        assertThat(userRepository.getAll().size()).isEqualTo(3);
    }

    @Test
    void loadUserByUsernameLoadsUser() {
        assertThat(userRepository.loadUserByUsername("Admin").get().getUsername()).isEqualTo("Admin");
    }
}
