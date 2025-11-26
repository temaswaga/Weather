package repository;

import model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import util.HibernateSessionFactoryUtil;

@Repository
public class UserRepository {

    public void save(User user) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.persist(user);
        }
    }

}
