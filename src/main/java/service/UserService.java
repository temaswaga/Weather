package service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;
import model.dto.SignUpDto;
import model.entity.Session;
import model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.SessionRepository;
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

    public boolean arePasswordsEqual(String password, String repeatedPassword) {
        return password.equals(repeatedPassword);
    }

    public User getUserBySessionId(UUID sessionId) {
        Session session = sessionService.getSessionBySessionId(sessionId);
        return userRepository.getById(session.getUserid());
    }
}
