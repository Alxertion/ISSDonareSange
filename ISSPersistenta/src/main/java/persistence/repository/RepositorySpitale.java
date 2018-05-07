package persistence.repository;

import model.Spital;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class RepositorySpitale implements IRepositorySpitale {

    private SessionFactory factory = null;

    public RepositorySpitale(){

        try {
            factory = HibernateFactory.getInstance();
        }
        catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

    }

    @Override
    public void adaugare(Spital spital) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(spital);
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
    public void modificare(Spital spital) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            session.update(spital);
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
    public Spital stergere(Integer id) {

        Transaction tx = null;
        Session session = null;
        Spital spital = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            spital = cautare(id);
            session.delete(spital);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return spital;

    }

    @Override
    public Spital cautare(Integer id) {

        Transaction tx = null;
        Session session = null;
        Spital spital = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            spital = (Spital) session.get(Spital.class, id);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return spital;

    }

    @Override
    public List<Spital> getAll() {

        Transaction tx = null;
        Session session = null;
        List<Spital> listOfAllSpitale = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            listOfAllSpitale = session.createQuery("from Spital ").list();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return listOfAllSpitale;

    }
}
