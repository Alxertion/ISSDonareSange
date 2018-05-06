package persistence.repository;

import model.Plasma;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * 
 */
public class RepositoryPlasma implements IRepositoryPlasma {

    /**
     * Default constructor
     */

    private SessionFactory factory = null;

    public RepositoryPlasma() {

        try {
            factory = HibernateFactory.getInstance();
        }
        catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    /**
     * @param
     */
    public void adaugare(Plasma pl) {

    }

    /**
     * @param
     */
    public void modificare( Plasma element) {
        // TODO implement here
    }

    /**
     * @param
     * @return
     */
    public Plasma stergere( Integer id) {
        // TODO implement here
        return null;
    }

    /**
     * @param
     * @return
     */
    public Plasma cautare( Integer id) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public List<Plasma> getAll() {
        // TODO implement here
        return null;
    }

}