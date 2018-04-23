package persistence.repository;

import model.Cont;
import model.PreparatSanguin;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
    public void modificare(Cont element) {

    }

    @Override
    public Cont stergere(Integer integer) {
        return null;
    }

    @Override
    public Cont cautare(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Cont> getAll() {
        return null;
    }
}
