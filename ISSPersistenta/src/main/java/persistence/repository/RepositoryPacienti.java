package persistence.repository;

import model.Pacient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * 
 */
public class RepositoryPacienti implements IRepositoryPacienti {

    /**
     * Default constructor
     */

    private SessionFactory factory = null;

    public RepositoryPacienti() {

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
    public void adaugare(Pacient element) {
        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(element);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * @param
     */
    public void modificare(Pacient element) {
        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            session.update(element);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * @param
     */
    public Pacient stergere(Integer id) {
        Transaction tx = null;
        Session session = null;
        Pacient element = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            element = cautare(id);
            session.delete(element);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return element;
    }

    /**
     * @param
     */
    public Pacient cautare(Integer id) {
        Transaction tx = null;
        Session session = null;
        Pacient pacient = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            pacient = (Pacient) session.get(Pacient.class, id);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return pacient;
    }

    /**
     *
     */
    public List<Pacient> getAll() {
        Transaction tx = null;
        List<Pacient> listOfAll = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            listOfAll = session.createQuery("from Pacient ").list();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return listOfAll;
    }

}