package persistence.repository;

import model.Boala;
import model.Cont;
import model.Donator;
import model.PreparatSanguin;
import model.TipPreparatSanguin;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

    @Override
    public Donator findDonatorByUsername(String username) {

        Transaction tx = null;
        Session session = null;
        Donator donator = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            Query query =  session.createQuery("From Donator WHERE cont.username = :numeuser");
            query.setParameter("numeuser", username);
            List list =  query.list();

            if(list.size()>0){
                donator = (Donator) list.get(0);
            }
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return donator;
    }

    @Override
    public Donator findDonatorByEmail(String semail) {
        Transaction tx = null;
        Session session = null;
        Donator donator = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            Query query =  session.createQuery("From Donator WHERE email = :email");
            query.setParameter("email", semail);
            donator = (Donator) query.list().get(0);

            tx.commit();
        } catch(IndexOutOfBoundsException e){
            // no problem
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return donator;
    }

    @Override
    public Donator findDonatorByCNP(String CNP) {
        Transaction tx = null;
        Session session = null;
        Donator donator = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            Query query =  session.createQuery("From Donator WHERE CNP = :CNP");
            query.setParameter("CNP", CNP);
            List list =  query.list();

            if(list.size()>0){
                donator = (Donator) list.get(0);
            }

            tx.commit();
        } catch(IndexOutOfBoundsException e){
            // no problem
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return donator;
    }

    @Override
    public boolean verificaUsername(String username) {
        Transaction tx = null;
        Session session = null;
        List rez=null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            org.hibernate.Query query = session.createSQLQuery("SELECT COUNT(*) FROM donator d WHERE d.username = ?");
            // nu este o eroare ci este doar obsolete (nu am gasit o alta metoda de a interoga baza de date)
            rez = query.setString(0, username).list();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        if(rez!=null) {
            if (!(rez.contains(null))) {
                if( Integer.parseInt(rez.get(0).toString()) > 0)
                    return true;
            }
        }
        return false;
    }
}