package gesha.web.interfaces.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import gesha.web.config.WebConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = {WebConfiguration.class})
@RunWith(SpringRunner.class)
public class AuthenticationControllerTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void equalUserNameAndPasswordAuthenticate() throws Exception {

        CredentialsDTO credentialsDTO = new CredentialsDTO("Admin", "admin");

        String response = mockMvc
                .perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(credentialsDTO))
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isNotNull();
    }

    @Test
    public void inEqualUsernameAndPasswordDontAuthenticate() throws Exception {
        CredentialsDTO credentialsDTO = new CredentialsDTO("admin", "wrongPassword");

        mockMvc
                .perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(credentialsDTO))
                )
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }
}
