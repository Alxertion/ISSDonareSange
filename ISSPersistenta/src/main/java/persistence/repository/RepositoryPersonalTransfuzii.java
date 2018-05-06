package persistence.repository;

import model.Medic;
import model.PersonalTransfuzii;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * 
 */
public class RepositoryPersonalTransfuzii implements IRepositoryPersonalTransfuzii {

    /**
     * Default constructor
     */

    private SessionFactory factory = null;

    public RepositoryPersonalTransfuzii() {

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
    public void adaugare(PersonalTransfuzii personalTransfuzii) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(personalTransfuzii);
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
    public void modificare(PersonalTransfuzii personalTransfuzii) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            session.update(personalTransfuzii);
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
    public PersonalTransfuzii stergere(Integer id) {

        Transaction tx = null;
        Session session = null;
        PersonalTransfuzii personalTransfuzii = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            personalTransfuzii = cautare(id);
            session.delete(personalTransfuzii);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return personalTransfuzii;

    }

    @Override
    public PersonalTransfuzii cautare(Integer id) {

        Transaction tx = null;
        Session session = null;
        PersonalTransfuzii personalTransfuzii = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            personalTransfuzii = (PersonalTransfuzii) session.get(PersonalTransfuzii.class, id);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return personalTransfuzii;


    }

    /**
     * @return
     */
    public List<PersonalTransfuzii> getAll() {

        Transaction tx = null;
        Session session = null;
        List<PersonalTransfuzii> listOfAllPersonalTransfuzii = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            listOfAllPersonalTransfuzii = session.createQuery("from PersonalTransfuzii ").list();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return listOfAllPersonalTransfuzii;

    }

}