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

    public User getById(Integer id) {
        String jpql = "SELECT u FROM User u WHERE u.id = :idParam";

        return entityManager.createQuery(jpql, User.class)
                .setParameter("idParam", id)
                .getSingleResult();
    }

    public User getByLogin(String login) {
        String jpql = "SELECT u FROM User u WHERE u.login = :loginParam";

        return entityManager.createQuery(jpql, User.class)
                .setParameter("loginParam", login)
                .getSingleResultOrNull();
    }

    public User loginExists(String login) {
        String jpql = "SELECT u FROM User u WHERE u.login = :loginParam";

        return entityManager.createQuery(jpql, User.class)
                .setParameter("loginParam", login)
                .getSingleResultOrNull();
    }
}