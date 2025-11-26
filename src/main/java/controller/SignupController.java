package controller;

import model.dto.SignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import service.UserService;

@Controller
public class SignupController {
    private final UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String getSignupPage(Model model) {
        model.addAttribute("user", new SignupDto());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signupSubmit(@ModelAttribute SignupDto userDto, Model model) {
        System.out.println("Username: " + userDto.getUsername());
        System.out.println("Password: " + userDto.getPassword());
        System.out.println("Repeat: " + userDto.getRepeatPassword());

        if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
            model.addAttribute("error", "Пароли не совпадают!");
            return "sign-up-with-errors";
        }

        // Здесь сохраняй пользователя
        // userService.save(userDto);

        return "redirect:/sign-in";  // редирект после успешной регистрации
    }
}