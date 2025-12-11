package controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import model.dto.SignInDto;
import model.entity.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import service.AuthService;
import service.CookieService;
import service.LocationService;
import service.SessionService;


@Controller
public class SignInController {
    private final CookieService cookieService;
    private final SessionService sessionService;
    private final AuthService authService;

    @Autowired
    public SignInController(CookieService cookieService, SessionService sessionService, AuthService authService, LocationService locationService) {
        this.cookieService = cookieService;
        this.sessionService = sessionService;
        this.authService = authService;
    }

    @GetMapping("/sign-in")
    public String getLoginPage(@ModelAttribute("signInDto") SignInDto signInDto) {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signInSubmit(@ModelAttribute("signInDto") @Valid SignInDto signInDto, HttpServletResponse response, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "sign-in-with-errors";
        }

        if (authService.isPasswordCorrect(signInDto)) {
            Session session = sessionService.createSession(signInDto);
            cookieService.setSessionIntoCookie(session, response);

            return "redirect:/";
        } else {
            bindingResult.rejectValue("password", "password.invalid");
            return "sign-in-with-errors";
        }
    }
}