package service;

import lombok.RequiredArgsConstructor;
import model.dto.SignInDto;
import model.entity.Session;
import model.entity.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.SessionRepository;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SessionService{
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

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

    public void deleteSession(String sessionId) {
        sessionRepository.deleteSessionById(UUID.fromString(sessionId));
    }

}
