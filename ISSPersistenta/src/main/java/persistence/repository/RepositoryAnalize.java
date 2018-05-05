package persistence.repository;

import model.Analiza;
import model.Boala;
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
public class RepositoryAnalize implements IRepositoryAnalize {

    private SessionFactory factory = null;

    /**
     * Default constructor
     */
    public RepositoryAnalize() {

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
    public void adaugare(Analiza analiza) {
        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(analiza);
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
    public void modificare(Analiza analiza) {
        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            session.update(analiza);
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
    public Analiza stergere(Integer id) {
        Transaction tx = null;
        Session session = null;
        Analiza analiza = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            analiza = cautare(id);
            session.delete(analiza);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return analiza;
    }

    /**
     * @param
     * @return
     */
    public Analiza cautare(Integer id) {
        Transaction tx = null;
        Session session = null;
        Analiza analiza = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            analiza = (Analiza) session.get(Analiza.class, id);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return analiza;
    }

    /**
     * @return
     */
    public List<Analiza> getAll() {
        Transaction tx = null;
        Session session = null;
        List<Analiza> listOfAllAnalize = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            listOfAllAnalize = session.createQuery("from Analiza ").list();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return listOfAllAnalize;
    }
}