package gesha.interfaces.authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import gesha.AbstractSpringWebMvcTest;


public class AuthenticationControllerTest extends AbstractSpringWebMvcTest {

    @Test
    public void equalUserNameAndPasswordAuthenticate() throws Exception {
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
