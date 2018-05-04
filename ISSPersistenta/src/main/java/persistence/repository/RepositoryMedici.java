package persistence.repository;

import model.Cerere;
import model.Cont;
import model.Medic;
import model.PreparatSanguin;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * 
 */
public class RepositoryMedici implements IRepositoryMedici {

    /**
     * Default constructor
     */

    private SessionFactory factory = null;

    public RepositoryMedici() {

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
    @Override
    public void adaugare(Medic medic) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(medic);
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
    @Override
    public void modificare( Medic medic) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            session.update(medic);
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
    public Medic stergere(Integer id) {

        Transaction tx = null;
        Session session = null;
        Medic medic = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            medic = cautare(id);
            session.delete(medic);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return medic;

    }

    @Override
    public Medic cautare(Integer id) {

        Transaction tx = null;
        Session session = null;
        Medic medic = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            medic = (Medic) session.get(Medic.class, id);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return medic;

    }

    /**
     * @return
     */
    public Iterable<Medic> getAll() {

        Transaction tx = null;
        Session session = null;
        List<Medic> listOfAllMedici = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            listOfAllMedici = session.createQuery("from Medic ").list();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return listOfAllMedici;
    }

}