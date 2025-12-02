package service;

import model.entity.Session;
import model.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.SessionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Transactional
    public void createSession(User user) {
        Session session = new Session();
        session.setUserid(user);

        LocalDateTime expiresAt = LocalDateTime.now().plusHours(2);

        session.setExpiresat(expiresAt);

        sessionRepository.save(session);
    }
}
