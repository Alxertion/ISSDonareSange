package persistence.repository;

import model.Boala;
import model.Medic;
import model.Pacient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RepositoryPacient implements IRepositoryPacient {

    private SessionFactory factory = null;

    public RepositoryPacient(){

        try {
            factory = HibernateFactory.getInstance();
        }
        catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

    }


    @Override
    public void adaugare(Pacient pacient) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(pacient);
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
    public void modificare(Pacient pacient) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            session.update(pacient);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public Pacient cautaPacientDupaCNP(String CNP){

        Transaction tx = null;
        Session session = null;
        Pacient pacient = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            Query query =  session.createQuery("From Pacient WHERE cnp = :cnpParam");
            query.setParameter("cnpParam", CNP);
            pacient = (Pacient) query.list().get(0);

            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return pacient;
    }

    @Override
    public Pacient stergere(Integer id) {

        Transaction tx = null;
        Session session = null;
        Pacient pacient = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            pacient = cautare(id);
            session.delete(pacient);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return pacient;

    }

    @Override
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

    @Override
    public List<Pacient> getAll() {

        Transaction tx = null;
        Session session = null;
        List<Pacient> listOfAllPacienti = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            listOfAllPacienti = session.createQuery("from Pacient ").list();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return listOfAllPacienti;

    }
}
