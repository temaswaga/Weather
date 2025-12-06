package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.entity.Session;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void deleteExpiredSessions() {
        System.out.println("Deleting session at " + LocalDateTime.now());

        LocalDateTime now = LocalDateTime.now();
        String jpql = "DELETE FROM Session s WHERE s.expiresat < :now";

        entityManager
            .createQuery(jpql)
            .setParameter("now", now)
            .executeUpdate();
    }

}
