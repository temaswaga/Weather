package service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import model.dto.SignInDto;
import model.dto.SignUpDto;
import model.entity.Session;
import model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveUserToDb(SignUpDto signUpDto) {
        User user = new User();

        String password = signUpDto.getPassword();

        String hashedPassword = BCrypt.withDefaults().hashToString(10, password.toCharArray());

        user.setLogin(signUpDto.getUsername());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    public void verifyPassword(SignInDto signInDto) {
        User user = userRepository.getByLogin(signInDto.getUsername());

        if (user == null) {
            throw new RuntimeException("User is not found");
        }

        String hashedPassword = user.getPassword();
        String rawPassword = signInDto.getPassword();

        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), hashedPassword.toCharArray());

        if (!result.verified) {
            throw new RuntimeException("Incorrect password");
        }
    }

}
