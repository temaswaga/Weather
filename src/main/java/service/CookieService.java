package service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Session;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    public void setSessionIntoCookie(Session session, HttpServletResponse response) {
        Cookie sessionCookie = new Cookie("MY_SESSION_ID", session.getId().toString());
        sessionCookie.setMaxAge(2 * 60 * 60);
        sessionCookie.setPath("/");
        sessionCookie.setHttpOnly(true);
        response.addCookie(sessionCookie);
    }
}
