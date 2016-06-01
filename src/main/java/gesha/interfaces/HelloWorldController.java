package gesha.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello-world")
public class HelloWorldController {

    @RequestMapping(method = RequestMethod.GET)
    public String helloWorld() {
        return "helloWorld";
    }
}