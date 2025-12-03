package repository;

import model.entity.Session;
import model.entity.User;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public User getById(User userId) {
        String jpql = "SELECT u FROM User u WHERE u.id = :idParam";

        return entityManager.createQuery(jpql, User.class)
                .setParameter("idParam", userId)
                .getSingleResult();
    }

    public User getByLogin(String login) {
        String jpql = "SELECT u FROM User u WHERE u.login = :loginParam";

        return entityManager.createQuery(jpql, User.class)
                .setParameter("loginParam", login)
                .getSingleResult();
    }
}