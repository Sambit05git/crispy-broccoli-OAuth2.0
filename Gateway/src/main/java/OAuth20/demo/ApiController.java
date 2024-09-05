package OAuth20.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
public class ApiController {

    @GetMapping("/api/user")
    public String getUser(Principal principal) {
        return "Hello, " + principal.getName() + "!";
    }
}