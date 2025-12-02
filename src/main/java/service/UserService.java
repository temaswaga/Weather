package service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;
import model.dto.SignUpDto;
import model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.SessionRepository;
import repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final SessionService sessionService;
    private final UserRepository userRepository;

    public boolean arePasswordsEqual(String password, String repeatedPassword) {
        return password.equals(repeatedPassword);
    }

    @Transactional
    public void save(SignUpDto signUpDto) {
        User user = new User();

        String password = signUpDto.getPassword();

        String hashedPassword = BCrypt.withDefaults().hashToString(10, password.toCharArray());

        user.setLogin(signUpDto.getUsername());
        user.setPassword(hashedPassword);

        userRepository.save(user);

        sessionService.createSession(user);
    }
}
