package gesha.features;

import gesha.AbstractSpringWebMvcTest;
import gesha.web.interfaces.authentication.CredentialsDTO;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthenticationFeatureTest extends AbstractSpringWebMvcTest{

    @Test
    public void resourceIsSecured() throws Exception {
        mockMvc
                .perform(get("/protected-resource"))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    public void invalidTokenDoesntWork() throws Exception {
        mockMvc
                .perform(
                        get("/protected-resource")
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
                        get("/protected-resource")
                                .header(HttpHeaders.AUTHORIZATION, authenticationToken)
                )
                .andExpect(status().is(HttpStatus.OK.value()));

    }
}
