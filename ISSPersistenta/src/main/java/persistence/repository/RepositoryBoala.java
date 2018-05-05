package persistence.repository;

import model.Analiza;
import model.Boala;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RepositoryBoala implements IRepositoryBoli {

    private SessionFactory factory = null;

    public RepositoryBoala(){
        try {
            factory = HibernateFactory.getInstance();
        }
        catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public void adaugare(Boala boala) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(boala);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public Boala cautareDupaNume(String numeBoala){

        Transaction tx = null;
        Session session = null;
        Boala boala = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            Query query =  session.createQuery("From Boala WHERE nume = :numeBoala");
            query.setParameter("numeBoala", numeBoala);
            boala = (Boala) query.list().get(0);

            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return boala;

    }

    @Override
    public void modificare(Boala boala) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            session.update(boala);
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
    public Boala stergere(Integer id) {

        Transaction tx = null;
        Session session = null;
        Boala boala = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            boala = cautare(id);
            session.delete(boala);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return boala;
    }

    @Override
    public Boala cautare(Integer id) {

        Transaction tx = null;
        Session session = null;
        Boala boala = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            boala = (Boala) session.get(Boala.class, id);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return boala;
    }

    @Override
    public List<Boala> getAll() {
        Transaction tx = null;
        Session session = null;
        List<Boala> listOfAllBoli = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            listOfAllBoli = session.createQuery("from Boala ").list();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return listOfAllBoli;
    }
}
