package service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Session;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    private void configureCookie(Cookie cookie) {
        cookie.setMaxAge(2 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
    }

    public void setSessionIntoCookie(Session session, HttpServletResponse response) {
        Cookie sessionCookie = new Cookie("MY_SESSION_ID", session.getId().toString());
        configureCookie(sessionCookie);
        response.addCookie(sessionCookie);
    }
}
