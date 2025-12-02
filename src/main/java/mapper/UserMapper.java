package mapper;

import model.dto.SignInDto;
import model.entity.User;

public class UserMapper {

    public static User fromSignInDto(SignInDto signInDto) {
        return new User(signInDto.getUsername(), signInDto.getPassword());
    }
}
