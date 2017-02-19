package application.api.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller is responsible for redirecting
 * users of the web app to the right pages.
 */
@Controller
public class WebController {

    /**
     * This endpoint redirects to the login page.
     * @return String
     */
    @RequestMapping("/login")
    public String login() {
        return "loginPage";
    }

}
