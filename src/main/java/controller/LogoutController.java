package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import service.SessionService;

@RequiredArgsConstructor
@Controller
public class LogoutController {
    private final SessionService sessionService;

    @PostMapping("/logout")
    public String getLoginPage(@CookieValue(value = "MY_SESSION_ID", required = false) String sessionId, Model model) {
        sessionService.deleteSession(sessionId);
        return "redirect:/sign-in";
    }
}
