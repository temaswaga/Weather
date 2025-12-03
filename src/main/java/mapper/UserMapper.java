package mapper;

import model.dto.SignInDto;
import model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import repository.UserRepository;


public class UserMapper {

    private UserRepository userRepository;

    public User fromSignInDto(SignInDto signInDto) {
        return userRepository.getByLogin(signInDto.getUsername());
    }
}
