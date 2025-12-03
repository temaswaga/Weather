package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.entity.Session;
import model.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public class SessionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Session session) {
        entityManager.persist(session);
    }

    public Session getById(UUID sessionId) {
        String jpql = "SELECT s FROM Session s WHERE s.id = :idParam";

        return entityManager.createQuery(jpql, Session.class)
                .setParameter("idParam", sessionId)
                .getSingleResult();
    }

}
