package repository;

import exceptions.EmptyResultDataAccessException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

        return entityManager
                .createQuery(jpql, Location.class)
                .setParameter("idParam", userId)
                .getResultList();
    }

    public void deleteLocation(long id) {
        Location location = entityManager.find(Location.class, id);

        if (location != null) {
            entityManager.remove(location);
        } else {
            throw new EmptyResultDataAccessException("Location not found");
        }
    }


}
