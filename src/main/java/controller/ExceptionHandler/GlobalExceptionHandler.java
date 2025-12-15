package controller.ExceptionHandler;

import exceptions.EmptyResultDataAccessException;
import exceptions.InvalidSessionException;
import exceptions.UnauthorizedDeleteAttemptException;
import exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import service.CookieService;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {
    private final CookieService cookieService;

    @ExceptionHandler(InvalidSessionException.class)
    public ModelAndView handleInvalidSession(InvalidSessionException ex, Model model, HttpServletResponse response) {
        model.addAttribute("error", ex.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        cookieService.deleteSessionCookie(response);
        return new ModelAndView("error");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ModelAndView handleException(EmptyResultDataAccessException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return new ModelAndView("redirect:/");
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ModelAndView handleUserExists(UserAlreadyExistsException ex, Model model, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        model.addAttribute("error", ex.getMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(UnauthorizedDeleteAttemptException.class)
    public ModelAndView handleUnauthorizedDeleteAttempt(UnauthorizedDeleteAttemptException ex, Model model, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        model.addAttribute("error", ex.getMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGlobalError(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ModelAndView("error");
    }
}
