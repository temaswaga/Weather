package controller;

import jakarta.servlet.http.HttpServletResponse;
import model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import service.UserService;

import java.util.UUID;

@Controller
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(@CookieValue(value = "MY_SESSION_ID", required = false) String sessionId, Model model) {

        if (sessionId == null) {
            return "redirect:/sign-in";
        }

        User user = userService.getUserBySessionId(UUID.fromString(sessionId)); // допустим если нет сессии в бд - возвращает нулл (надо проверить я не уверен)

        if (user == null) {
            return "redirect:/sign-in";
        }

        model.addAttribute("user", user);
        return "index";
    }
}