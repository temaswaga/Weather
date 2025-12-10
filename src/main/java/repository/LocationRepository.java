package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import model.entity.Location;
import model.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class LocationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Location location) {
        entityManager.persist(location);
    }

    public List<Location> getUsersLocations(User userId) {
        String jpql = "SELECT l FROM Location l WHERE l.userid = :idParam";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("idParam", userId);
        return query.getResultList();
    }


}
