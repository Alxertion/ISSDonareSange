package persistence.repository;

import model.PreparatSanguin;
import model.Trombocite;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * 
 */
public class RepositoryTrombocite implements IRepositoryTrombocite {

    /**
     * Default constructor
     */

    private SessionFactory factory = null;

    public RepositoryTrombocite() {

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
    public void adaugare(Trombocite element) {
        // TODO implement here
    }

    /**
     * @param
     */
    public void modificare(Trombocite element) {
        // TODO implement here
    }

    /**
     * @param
     */
    public Trombocite stergere(Integer id) {
        // TODO implement here
        return null;
    }

    /**
     * @param
     */
    public Trombocite cautare(Integer id) {
        // TODO implement here
        return null;
    }

    /**
     *
     */
    public List<Trombocite> getAll() {
        // TODO implement here
        return null;
    }

}