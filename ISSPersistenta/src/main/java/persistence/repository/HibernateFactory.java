package persistence.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {

    private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private HibernateFactory() {}

    public static SessionFactory getInstance() {
        return factory;
    }

    public static void closeFactory(){
        factory.close();
    }
}
