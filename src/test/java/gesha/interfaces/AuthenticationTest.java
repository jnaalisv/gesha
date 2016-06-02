package gesha.interfaces;

import gesha.interfaces.authentication.CredentialsDTO;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthenticationTest extends AbstractSpringWebMvcTest{

    @Test
    public void resourceIsSecured() throws Exception {
        mockMvc
                .perform(get("/protected-resource"))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    public void authenticateReturnsToken() throws Exception {
        CredentialsDTO credentialsDTO = new CredentialsDTO("admin", "admin");

        String response = mockMvc
                .perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(credentialsDTO))
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isNotNull();
    }
}
