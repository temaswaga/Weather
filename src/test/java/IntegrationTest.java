import config.TestConfig;
import config.WebConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import model.entity.Session;
import model.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import repository.SessionRepository;

import service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class, TestConfig.class})
@ActiveProfiles("test")
@WebAppConfiguration
@Transactional
public class IntegrationTest {
    private final UserService userService;
    private final SessionRepository sessionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Ошибка при создании пользователя с существующим логином")
    void testDuplicateUserRegistration() {
        User user1 = new User();
        user1.setLogin("unique_login");
        user1.setPassword("12345");
        userService.save(user1);

        entityManager.flush();

        User user2 = new User();
        user2.setLogin("unique_login");
        user2.setPassword("67890");

        assertThrows(PersistenceException.class, () -> {
            userService.save(user2);
            entityManager.flush();
        });
    }

    @Test
    @DisplayName("Сессия считается невалидной, если время истекло")
    void testSessionExpiration() {
        User user = new User();
        user.setLogin("session_user");
        user.setPassword("pass");
        userService.save(user);

        Session expiredSession = new Session();
        expiredSession.setUserid(user);
        expiredSession.setExpiresat(LocalDateTime.now().minusDays(1)); // Вчера

        sessionRepository.save(expiredSession);

        assertThrows(exceptions.InvalidSessionException.class, () -> {
            userService.getUserBySessionId(expiredSession.getId().toString());
        });
    }

    @Test
    @DisplayName("Сессия валидна, если время не истекло")
    void testValidSession() {
        User user = new User();
        user.setLogin("valid_user");
        user.setPassword("pass");
        userService.save(user);

        Session activeSession = new Session();
        activeSession.setUserid(user);
        activeSession.setExpiresat(LocalDateTime.now().plusDays(1));

        sessionRepository.save(activeSession);

        Optional<User> result = Optional.ofNullable(userService.getUserBySessionId(activeSession.getId().toString()));

        assertTrue(result.isPresent(), "Активная сессия должна возвращать пользователя");
        assertEquals("valid_user", result.get().getLogin());
    }
}
