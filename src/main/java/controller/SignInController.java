package controller;

import model.dto.SignInDto;
import model.dto.SignUpDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class SignInController {

    @GetMapping("/sign-in")
    public String getLoginPage(){
        return "sign-in";
    }

}
