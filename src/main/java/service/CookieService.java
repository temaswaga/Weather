package service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Session;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    private void configure2hCookie(Cookie cookie) {
        cookie.setMaxAge(2 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
    }

    public void setSessionIntoCookie(Session session, HttpServletResponse response) {
        Cookie sessionCookie = new Cookie("MY_SESSION_ID", session.getId().toString());
        configure2hCookie(sessionCookie);
        response.addCookie(sessionCookie);
    }

    public void deleteSessionCookie(HttpServletResponse response) {
        Cookie sessionCookie = new Cookie("MY_SESSION_ID", null);
        configure2hCookie(sessionCookie);
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
    }
}
