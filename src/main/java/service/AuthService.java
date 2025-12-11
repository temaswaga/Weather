package service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;
import model.dto.SignInDto;
import model.dto.SignUpDto;
import model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import repository.UserRepository;
import util.validation.SignUpPasswordValidation;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    @Transactional
    public void saveUserToDb(SignUpDto signUpDto) {
        User user = new User();

        String password = signUpDto.getPassword();

        String hashedPassword = BCrypt.withDefaults().hashToString(10, password.toCharArray());

        user.setLogin(signUpDto.getUsername());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    public boolean isPasswordCorrect(SignInDto signInDto) {
        User user = userRepository.getByLogin(signInDto.getUsername());

        if (user == null) {
            return false;
        }

        String hashedPassword = user.getPassword();
        String rawPassword = signInDto.getPassword();

        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), hashedPassword.toCharArray());

        return result.verified;
    }

    public boolean areUsersDetailsValid(SignUpDto user, BindingResult bindingResult) {

        if (userRepository.loginExists(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.username.exists", "Username already exists");
            return false;
        }

        if (SignUpPasswordValidation.validate(user.getPassword()) != null) {
            bindingResult.rejectValue("password", "error.password.valid", Objects.requireNonNull(SignUpPasswordValidation.validate(user.getPassword())));
        }

        if (!user.getPassword().equals(user.getRepeatedPassword())) {
            bindingResult.rejectValue("repeatedPassword", "error.pwd.match", "Passwords do not match");
            return false;
        }

        return true;
    }

}
