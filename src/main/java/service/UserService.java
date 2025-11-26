package service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public boolean arePasswordsEqual(String password, String repeatedPassword) {
        return password.equals(repeatedPassword);
    }
}
