package persistence.repository;

import model.SangeNefiltrat;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * 
 */
public class RepositorySangeNefiltrat implements IRepositorySangeNefiltrat {

    /**
     * Default constructor
     */

    private SessionFactory factory = null;


    public RepositorySangeNefiltrat() {

        try {
            factory = HibernateFactory.getInstance();
        }
        catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }




    /**
     * @param element
     */
    public void adaugare(SangeNefiltrat element) {
        // TODO implement here
    }

    /**
     * @param
     */
    public void modificare( SangeNefiltrat element) {
        // TODO implement here
    }

    /**
     * @param
     * @return
     */
    public SangeNefiltrat stergere(Integer id) {
        // TODO implement here
        return null;
    }

    /**
     * @param
     * @return
     */
    public SangeNefiltrat cautare(Integer id) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public List<SangeNefiltrat> getAll() {
        // TODO implement here
        return null;
    }

}