package gesha.web.interfaces.user;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gesha.model.application.UserService;
import gesha.model.domain.user.User;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<UserDTO> getAll() {
        return userService
                .getAll()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> createNew(@RequestBody UserDTO userDTO) {

        User user = new User(userDTO.username, userDTO.password);

        userService.saveNew(user);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "users/" + user.getID());
        return new ResponseEntity<>(new UserDTO(user), responseHeaders, HttpStatus.CREATED);
    }
}