package persistence.repository;

import model.Analiza;
import model.Cerere;
import model.Cont;
import model.PreparatSanguin;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * 
 */
public class RepositoryCereri implements IRepositoryCereri {

    /**
     * Default constructor
     */
    private SessionFactory factory = null;

    public RepositoryCereri() {

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
    public void adaugare( Cerere cerere) {
        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(cerere);
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
    public void modificare(Cerere cerere) {
        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            session.update(cerere);
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
     * @return
     */
    public Cerere stergere( Integer id) {
        Transaction tx = null;
        Session session = null;
        Cerere cerere = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            cerere = cautare(id);
            session.delete(cerere);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return cerere;

    }

    /**
     * @param
     * @return
     */
    public Cerere cautare( Integer id) {
        Transaction tx = null;
        Session session = null;
        Cerere cerere = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            cerere = (Cerere) session.get(Cerere.class, id);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return cerere;
    }

    /**
     * @return
     */
    public List<Cerere> getAll() {
        Transaction tx = null;
        Session session = null;
        List<Cerere> listOfAllCereri = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            listOfAllCereri = session.createQuery("from Cerere ").list();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return listOfAllCereri;
    }

}