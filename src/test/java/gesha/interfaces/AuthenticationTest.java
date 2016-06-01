package gesha.interfaces;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthenticationTest extends AbstractSpringWebMvcTest{

    @Test
    public void resourceIsSecured() throws Exception {
        mockMvc
                .perform(get("/protected"))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }
}
