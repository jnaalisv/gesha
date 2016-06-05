package gesha;

import com.fasterxml.jackson.databind.ObjectMapper;

import gesha.infrastructure.config.PersistenceConfiguration;
import gesha.model.config.DomainConfiguration;
import gesha.web.config.WebConfiguration;
import gesha.web.config.security.SpringSecurityConfiguration;
import gesha.web.config.security.WebSecurityConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {PersistenceConfiguration.class, DomainConfiguration.class, WebSecurityConfig.class, WebConfiguration.class })
public abstract class AbstractSpringWebMvcTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .build();
    }

}
