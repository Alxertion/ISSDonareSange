package persistence.repository;

import model.Cont;
import model.PreparatSanguin;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class RepositoryConturi implements IRepositoryConturi {

    private SessionFactory factory = null;

    public RepositoryConturi(){
        try {
            factory = HibernateFactory.getInstance();
        }
        catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public void adaugare(Cont cont) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(cont);
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
    public void modificare(Cont cont) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            session.update(cont);
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
    public Cont stergere(String username) {

        Transaction tx = null;
        Session session = null;
        Cont cont = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            cont = cautare(username);
            session.delete(cont);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return cont;

    }

    @Override
    public Cont cautare(String username) {

        Transaction tx = null;
        Session session = null;
        Cont cont = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            cont = (Cont) session.get(Cont.class, username);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return cont;
    }


    @Override
    public List<Cont> getAll() {

        Transaction tx = null;
        Session session = null;
        List<Cont> listOfAllConturi = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            listOfAllConturi = session.createQuery("from Cont").list();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return listOfAllConturi;
    }
}
