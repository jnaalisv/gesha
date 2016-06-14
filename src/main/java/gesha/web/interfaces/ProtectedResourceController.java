package gesha.web.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedResourceController {

    @GetMapping("protected-resource")
    public String authorizedAccessOnly() {
        return "user is authorized";
    }
}