package gesha.features;

import com.fasterxml.jackson.databind.ObjectMapper;
import gesha.web.config.WebConfiguration;
import gesha.web.interfaces.authentication.CredentialsDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = {WebConfiguration.class})
@RunWith(SpringRunner.class)
public class AuthenticationFeatureTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void resourceIsSecured() throws Exception {
        mockMvc
                .perform(get("/users"))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    public void invalidTokenDoesntWork() throws Exception {
        mockMvc
                .perform(
                        get("/users")
                        .header(HttpHeaders.AUTHORIZATION, "not a valid token")
                )
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    public void authenticationTokenAuthorizesAccessToProtectedResources() throws Exception {

        CredentialsDTO credentialsDTO = new CredentialsDTO("Admin", "admin");

        String authenticationToken = mockMvc
                .perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(credentialsDTO))
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();

        mockMvc
                .perform(
                        get("/users")
                                .header(HttpHeaders.AUTHORIZATION, authenticationToken)
                )
                .andExpect(status().is(HttpStatus.OK.value()));

    }
}
