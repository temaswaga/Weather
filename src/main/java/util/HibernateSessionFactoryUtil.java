package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static final SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        return HibernateSessionFactoryUtil.sessionFactory;
    }

    static {
        try {
            Configuration config = new Configuration();
            config.configure();
            sessionFactory = config.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
