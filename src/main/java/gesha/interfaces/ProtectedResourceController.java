package gesha.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("protected")
public class ProtectedResourceController {

    @RequestMapping(method = RequestMethod.GET)
    public String authorizedAccessOnly() {
        return "user is authorized";
    }
}