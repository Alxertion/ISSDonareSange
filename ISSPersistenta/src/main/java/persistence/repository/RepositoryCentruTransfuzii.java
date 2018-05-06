package persistence.repository;

import model.Analiza;
import model.CentruTransfuzii;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class RepositoryCentruTransfuzii implements IRepositoryCentruTransfuzii {

    private SessionFactory factory = null;

    public RepositoryCentruTransfuzii(){

        try {
            factory = HibernateFactory.getInstance();
        }
        catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

    }

    @Override
    public void adaugare(CentruTransfuzii centruTransfuzii) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(centruTransfuzii);
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
    public void modificare(CentruTransfuzii centruTransfuzii) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            session.update(centruTransfuzii);
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
    public CentruTransfuzii stergere(Integer id) {

        Transaction tx = null;
        Session session = null;
        CentruTransfuzii centruTransfuzii = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            centruTransfuzii = cautare(id);
            session.delete(centruTransfuzii);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return centruTransfuzii;

    }

    @Override
    public CentruTransfuzii cautare(Integer id) {

        Transaction tx = null;
        Session session = null;
        CentruTransfuzii centruTransfuzii = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            centruTransfuzii = (CentruTransfuzii) session.get(CentruTransfuzii.class, id);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return centruTransfuzii;

    }

    @Override
    public List<CentruTransfuzii> getAll() {

        Transaction tx = null;
        Session session = null;
        List<CentruTransfuzii> listOfAllCentreTransfuzii = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            listOfAllCentreTransfuzii = session.createQuery("from CentruTransfuzii ").list();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return listOfAllCentreTransfuzii;

    }
}
