package controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import model.dto.SignInDto;
import model.dto.SignUpDto;
import model.entity.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import service.AuthService;
import service.CookieService;
import service.SessionService;


@Controller
public class SignInController {
    private final CookieService cookieService;
    private final SessionService sessionService;
    private final AuthService authService;

    @Autowired
    public SignInController(CookieService cookieService, SessionService sessionService, AuthService authService) {
        this.cookieService = cookieService;
        this.sessionService = sessionService;
        this.authService = authService;
    }

    @GetMapping("/sign-in")
    public String getLoginPage(@ModelAttribute SignInDto signInDto, HttpServletResponse response, Model model) {
        model.addAttribute("SignInDto", signInDto);
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signinSubmit(@ModelAttribute("signInDto") @Valid SignInDto signInDto, HttpServletResponse response, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "sign-in";
        }

        try {
            authService.verifyPassword(signInDto);
            Session session = sessionService.createSession(signInDto);

            cookieService.setSessionIntoCookie(session, response);

            return "redirect:/home";
        } catch (Exception e) {

            return "sign-in-with-errors";
        }
    }
    
}
