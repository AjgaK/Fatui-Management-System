package mas.mas_14c_kordula_iga_s24646.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for handling operations related to displaying the home page.
 */
@Controller
public class HomeController {

    /**
     * Handles GET requests for the home page.
     *
     * @return the name of the home view
     */
    @GetMapping("/home")
    public String home() {
        return "Home";
    }
}
