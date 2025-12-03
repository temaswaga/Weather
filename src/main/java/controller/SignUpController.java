package controller;

import model.dto.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import service.AuthService;

@Controller
public class SignUpController {
    @Autowired
    private AuthService authService;

    @GetMapping("/sign-up")
    public String getSignupPage(Model model) {
        model.addAttribute("user", new SignUpDto());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signupSubmit(@ModelAttribute SignUpDto signUpDto, Model model) {

        authService.saveUserToDb(signUpDto);

        return "redirect:/sign-in";
    }
}