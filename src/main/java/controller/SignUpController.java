package controller;

import jakarta.validation.Valid;
import model.dto.SignUpDto;
import model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import repository.UserRepository;
import service.AuthService;


@Controller
public class SignUpController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @Autowired
    public SignUpController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @GetMapping("/sign-up")
    public String getSignupPage(Model model) {
        model.addAttribute("user", new SignUpDto());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute("user") @Valid SignUpDto user,
                         BindingResult bindingResult,
                         Model model) {

        if (!user.getPassword().equals(user.getRepeatedPassword())) {
            bindingResult.rejectValue("repeatedPassword", "error.pwd.match", "Passwords do not match");
        }

        if (userRepository.loginExists(user.getUsername())) {

        }

        if (bindingResult.hasErrors()) {
            return "sign-up";
        }

        return "redirect:/sign-in";
    }
}