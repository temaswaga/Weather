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
import service.SessionService;


@Controller
public class SignInController {
    private final SessionService sessionService;

    @Autowired
    public SignInController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/sign-in")
    public String getLoginPage(@ModelAttribute SignInDto signInDto, HttpServletResponse response){
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signinSubmit(@ModelAttribute SignInDto signInDto, HttpServletResponse response) {

        // валидация - верный ли пароль и логин

        Session session = sessionService.createSession(signInDto);

        if (session != null) {
            Cookie sessionCookie = new Cookie("MY_SESSION_ID", session.getId().toString());
            sessionCookie.setMaxAge(2 * 60 * 60);
            sessionCookie.setPath("/");
            sessionCookie.setHttpOnly(true);
            response.addCookie(sessionCookie);

            return "redirect:/home";
        } else {
            return "sign-in-with-errors";
        }
    }

}
