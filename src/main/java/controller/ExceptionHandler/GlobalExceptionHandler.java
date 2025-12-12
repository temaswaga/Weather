package controller.ExceptionHandler;

import exceptions.EmptyResultDataAccessException;
import exceptions.InvalidSessionException;
import exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import model.dto.SignUpDto;
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
    public ModelAndView handleUserExists(UserAlreadyExistsException ex) {
        ex.printStackTrace();
        return new ModelAndView("error");
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGlobalError(Exception ex) {
        ex.printStackTrace();
        return new ModelAndView("error");
    }
}
