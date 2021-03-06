package gesha.web.interfaces.user;

import gesha.domain.UserService;
import gesha.domain.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAll() {
        return userService
                .getAll()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createNew(@RequestBody UserDTO userDTO) {

        User user = new User(userDTO.username, userDTO.password);

        userService.saveNew(user);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "users/" + user.getID());
        return new ResponseEntity<>(new UserDTO(user), responseHeaders, HttpStatus.CREATED);
    }
}
