package service;

import exceptions.InvalidSessionException;
import lombok.RequiredArgsConstructor;
import model.entity.Session;
import model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final SessionService sessionService;
    private final UserRepository userRepository;

    public User getUserBySessionId(String sessionId) {
        Session session = sessionService.getSessionBySessionId(UUID.fromString(sessionId));
        if (session == null) {
            throw new InvalidSessionException("Session not found");
        }

        return userRepository.getById(session.getUserid().getId());
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getByLogin(String login) {
        return userRepository.getByLogin(login);
    }
}
