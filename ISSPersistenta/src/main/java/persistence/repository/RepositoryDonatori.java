package persistence.repository;

import model.Cont;
import model.Donator;
import model.PreparatSanguin;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * 
 */
public class RepositoryDonatori implements IRepositoryDonatori {

    private SessionFactory factory = null;

    /**
     * Default constructor
     */
    public RepositoryDonatori() {

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
     * @return
     */
    public void adaugare(Donator donator) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(donator);
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
    public void modificare(Donator donator) {
        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            Donator newDonator = cautare(donator.getIdDonator());
            newDonator.setAllFields(donator);
            session.update(donator);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Donator stergere(Integer id) {
        Transaction tx = null;
        Session session = null;
        Donator donator = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            donator = cautare(id);
            session.delete(donator);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return donator;
    }

    @Override
    public Donator cautare(Integer id) {
        Transaction tx = null;
        Session session = null;
        Donator donator = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            donator = (Donator) session.get(Donator.class, id);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return donator;
    }

    /**
     * @return
     */
    public List<Donator> getAll() {
        Transaction tx = null;
        Session session = null;
        List<Donator> listOfAllDonatori = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            listOfAllDonatori = session.createQuery("from Donator").list();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return listOfAllDonatori;
    }

}