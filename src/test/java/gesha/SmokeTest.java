package gesha;

import gesha.web.interfaces.authentication.CredentialsDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = GeshaApplication.class
)
public class SmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void cantFetchSecuredResourceWithoutAuthentication() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> usersResponse = restTemplate.exchange("/users", HttpMethod.GET, requestEntity, String.class);
        assertThat(usersResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void shouldAuthenticate() {
        CredentialsDTO credentialsDTO = new CredentialsDTO("Admin", "admin");

        ResponseEntity<String> authenticationResponse = restTemplate.postForEntity("/authenticate", credentialsDTO, String.class);

        assertThat(authenticationResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        String token = authenticationResponse.getBody();
        assertThat(token).isNotEmpty();
    }

    @Test
    public void shouldBeAbleToFetchUsersWithAuthToken() {
        CredentialsDTO credentialsDTO = new CredentialsDTO("Admin", "admin");

        ResponseEntity<String> authenticationResponse = restTemplate.postForEntity("/authenticate", credentialsDTO, String.class);
        String token = authenticationResponse.getBody();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + token);

        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> usersResponse = restTemplate.exchange("/users", HttpMethod.GET, requestEntity, String.class);
        assertThat(usersResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void invalidUserNamePasswordShouldNotAuthenticate() {
        CredentialsDTO credentialsDTO = new CredentialsDTO("Admin", "asdas");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/authenticate", credentialsDTO, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

}
