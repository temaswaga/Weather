package service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import mapper.UserMapper;
import model.dto.SignInDto;
import model.entity.Session;
import model.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.SessionRepository;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService{
    private final UserMapper userMapper =  new UserMapper();
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Session createSession(SignInDto signInDto) {
        User user = userRepository.getByLogin(signInDto.getUsername());

        if (user == null) {
            throw new RuntimeException("Пользователь не найден");
        }

        Session session = new Session();
        session.setUserid(user);

        LocalDateTime expiresAt = LocalDateTime.now().plusHours(2);
        session.setExpiresat(expiresAt);

        sessionRepository.save(session);

        return session;
    }

    public Session getSessionBySessionId(UUID sessionId) {
        return sessionRepository.getById(sessionId);
    }



}
