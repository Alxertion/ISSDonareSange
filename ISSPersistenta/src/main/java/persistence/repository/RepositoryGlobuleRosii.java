package persistence.repository;

import model.GlobuleRosii;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * 
 */
public class RepositoryGlobuleRosii implements IRepositoryGlobuleRosii {

    /**
     * Default constructor
     */

    private SessionFactory factory = null;

    public RepositoryGlobuleRosii() {

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
    public void adaugare(GlobuleRosii element) {
        // TODO implement here
    }

    /**
     * @param
     */
    public void modificare(GlobuleRosii element) {
        // TODO implement here
    }

    /**
     * @param
     * @return
     */
    public GlobuleRosii stergere(Integer id) {
        // TODO implement here
        return null;
    }

    /**
     * @param
     * @return
     */
    public GlobuleRosii cautare( Integer id) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public List<GlobuleRosii> getAll() {
        // TODO implement here
        return null;
    }

}