package controller;

import jakarta.validation.Valid;
import model.dto.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import service.AuthService;


@Controller
public class SignUpController {
    private final AuthService authService;

    @Autowired
    public SignUpController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/sign-up")
    public String getSignupPage(Model model) {
        model.addAttribute("user", new SignUpDto());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute("user") @Valid SignUpDto user, BindingResult bindingResult) {

        if (!authService.areUsersDetailsValid(user, bindingResult) || bindingResult.hasErrors()) {
            return "sign-up-with-errors";
        }

        authService.saveUserToDb(user);

        return "redirect:/sign-in";
    }

}