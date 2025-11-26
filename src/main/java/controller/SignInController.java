package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SigninController {

    @GetMapping("/sign-in")
    public String getLoginPage(){
        return "sign-in";
    }


}
