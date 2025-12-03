package controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.SignInDto;
import model.dto.SignUpDto;
import model.entity.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import service.AuthService;
import service.CookieService;
import service.SessionService;


@Controller
public class SignInController {
    @Autowired
    private CookieService cookieService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private AuthService authService;

    @GetMapping("/sign-in")
    public String getLoginPage(@ModelAttribute SignInDto signInDto, HttpServletResponse response){
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signinSubmit(@ModelAttribute SignInDto signInDto, HttpServletResponse response, Model model) {

        try {
            authService.verifyPassword(signInDto);
            Session session = sessionService.createSession(signInDto);

            if (session != null) {

                cookieService.setSessionIntoCookie(session, response);

                return "redirect:/home";
            } else {
                return "sign-in-with-errors";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            model.addAttribute("lastUsername", signInDto.getUsername());

            return "sign-in-with-errors";
        }
    }

}
