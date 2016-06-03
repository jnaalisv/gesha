package gesha.interfaces;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gesha.AbstractSpringWebMvcTest;

public class HelloWorldControllerTest extends AbstractSpringWebMvcTest {

    @Test
    public void testHelloWorld() throws Exception {
        String response = mockMvc
                .perform(get("/hello-world"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo("helloWorld");
    }
}
