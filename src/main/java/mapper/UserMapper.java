package mapper;

import model.dto.SignInDto;
import model.entity.User;
import repository.SessionRepository;

public class UserMapper {

    public static User fromSignInDto(SignInDto signInDto) {
        return new User(signInDto.getUsername(), signInDto.getPassword());
    }
}
