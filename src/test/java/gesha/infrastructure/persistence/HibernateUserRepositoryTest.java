package gesha.infrastructure.persistence;

import gesha.infrastructure.config.PersistenceConfiguration;
import gesha.model.domain.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(classes = {PersistenceConfiguration.class})
@RunWith(SpringRunner.class)
public class HibernateUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAllFindsAllUsers() {
        assertThat(userRepository.getAll().size()).isEqualTo(3);
    }

    @Test
    public void loadUserByUsernameLoadsUser() {
        assertThat(userRepository.loadUserByUsername("Admin").get().getUsername()).isEqualTo("Admin");
    }
}
