package service;

import exceptions.InvalidSessionException;
import model.entity.Session;
import model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.UUID;

@Service
public class UserService {
    private final SessionService sessionService;
    private final UserRepository userRepository;

    @Autowired
    public UserService(SessionService sessionService, UserRepository userRepository) {
        this.sessionService = sessionService;
        this.userRepository = userRepository;
    }

    public User getUserBySessionId(String sessionId) {
        Session session = sessionService.getSessionBySessionId(UUID.fromString(sessionId));
        if (session == null) {
            throw new InvalidSessionException("Session not found");
        }

        return userRepository.getById(session.getUserid().getId());
    }
}
