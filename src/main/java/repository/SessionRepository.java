package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.entity.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SessionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Session session) {
        entityManager.persist(session);
    }

}
