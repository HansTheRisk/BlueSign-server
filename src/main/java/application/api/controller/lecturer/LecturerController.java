package application.api.controller.lecturer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LecturerController {

    @RequestMapping("/lecturer")
    public String lecturer() {
        return "index";
    }

}
