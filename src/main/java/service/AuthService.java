package service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import model.dto.SignInDto;
import model.dto.SignUpDto;
import model.entity.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import util.validation.SignUpPasswordValidation;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserService userService;

    public void saveUserToDb(SignUpDto signUpDto) {
        User user = new User();

        String password = signUpDto.getPassword();

        String hashedPassword = BCrypt.withDefaults().hashToString(10, password.toCharArray());

        user.setLogin(signUpDto.getUsername());
        user.setPassword(hashedPassword);

        try { // race condition protection
            userService.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("Пользователь с таким логином уже существует");
        }
    }

    public boolean isPasswordCorrect(SignInDto signInDto) {
        User user = userService.getByLogin(signInDto.getUsername());

        if (user == null) {
            return false;
        }

        String hashedPassword = user.getPassword();
        String rawPassword = signInDto.getPassword();

        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), hashedPassword.toCharArray());

        return result.verified;
    }

    public boolean areUsersDetailsValid(SignUpDto user, BindingResult bindingResult) {

        if (userService.getByLogin(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.username.exists", "Username already exists");
            return false;
        }

        String errorMessage = SignUpPasswordValidation.validate(user.getPassword());
        if (errorMessage != null) {
            bindingResult.rejectValue("password", "error.password.valid", errorMessage);
        }

        if (!user.getPassword().equals(user.getRepeatedPassword())) {
            bindingResult.rejectValue("repeatedPassword", "error.pwd.match", "Passwords do not match");
            return false;
        }

        return true;
    }

}
