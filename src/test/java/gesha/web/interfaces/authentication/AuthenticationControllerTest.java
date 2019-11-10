package gesha.web.interfaces.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AuthenticationController.class})
class AuthenticationControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void equalUserNameAndPasswordAuthenticate() throws Exception {

        CredentialsDTO credentialsDTO = new CredentialsDTO("Admin", "admin");

        String response = mockMvc
                .perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(credentialsDTO))
                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isNotNull();
    }

    @Test
    void inEqualUsernameAndPasswordDontAuthenticate() throws Exception {
        CredentialsDTO credentialsDTO = new CredentialsDTO("admin", "wrongPassword");

        mockMvc
                .perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(credentialsDTO))
                )
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }
}
