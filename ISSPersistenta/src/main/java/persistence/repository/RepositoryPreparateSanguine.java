package persistence.repository;

import model.Cont;
import model.Donator;
import model.PreparatSanguin;
import model.TipPreparatSanguin;
import org.hibernate.*;

import java.util.List;

public class RepositoryPreparateSanguine implements IRepositoryPreparateSanguine {

    private SessionFactory factory = null;

    public RepositoryPreparateSanguine(){

        try {
            factory = HibernateFactory.getInstance();
        }
        catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public void adaugare(PreparatSanguin preparatSanguin) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(preparatSanguin);
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
    public void modificare(PreparatSanguin preparatSanguin) {

        Transaction tx = null;
        Session session = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            session.update(preparatSanguin);
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
    public PreparatSanguin stergere(Integer id) {
        Transaction tx = null;
        Session session = null;
        PreparatSanguin preparatSanguin = null;
        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            preparatSanguin = cautare(id);
            session.delete(preparatSanguin);
            tx.commit();

        }catch (HibernateException e){
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return preparatSanguin;
    }

    @Override
    public PreparatSanguin cautare(Integer id) {

        Transaction tx = null;
        Session session = null;
        PreparatSanguin preparatSanguin = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            preparatSanguin = (PreparatSanguin) session.get(PreparatSanguin.class, id);
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return preparatSanguin;
    }


    public List<PreparatSanguin> cautareUltimeleNPreparateSanguine(int n){

        Transaction tx = null;
        Session session = null;
        List<PreparatSanguin> preparateSanguine = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            preparateSanguine =  session.createQuery("FROM PreparatSanguin ORDER BY idPreparatSanguin DESC").setMaxResults(n).list();

            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return preparateSanguine;

    }

    @Override
    public int findIdDonatorForPreparatSanguin(int idPreparatSanguin) {
        Transaction tx = null;
        Session session = null;
        int idDonator=-1;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            Query query = session.createSQLQuery("SELECT PS.idDonator FROM preparatsanguin PS WHERE PS.idPreparatSanguin = ?");
            // nu este o eroare ci este doar obsolete (nu am gasit o alta metoda de a interoga baza de date)
            List idDonatorList = query.setInteger(0, idPreparatSanguin).list();

            if(!idDonatorList.contains(null)){
                idDonator = (int)idDonatorList.get(0);
            }

            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return idDonator;
    }

    @Override
    public int cautarePacientDupaPreparat(int idPreparatSanguin) {
        Transaction tx = null;
        Session session = null;
        int idPacient=-1;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            Query query = session.createSQLQuery("SELECT PS.idPacient FROM preparatsanguin PS WHERE PS.idPreparatSanguin = ?");
            // nu este o eroare ci este doar obsolete (nu am gasit o alta metoda de a interoga baza de date)
            List idPacientList = query.setInteger(0, idPreparatSanguin).list();

            if(!idPacientList.contains(null)){
                idPacient = (int)idPacientList.get(0);
            }

            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return idPacient;
    }

    @Override
    public int cautareAnalizaDupaPreparat(int idPreparatSanguin) {
        Transaction tx = null;
        Session session = null;
        int idAnaliza=-1;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();

            Query query = session.createSQLQuery("SELECT PS.idAnaliza FROM preparatsanguin PS WHERE PS.idPreparatSanguin = ?");
            // nu este o eroare ci este doar obsolete (nu am gasit o alta metoda de a interoga baza de date)
            List idAnalizaList = query.setInteger(0, idPreparatSanguin).list();

            if(!idAnalizaList.contains(null)){
                idAnaliza = (int)idAnalizaList.get(0);
            }

            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return idAnaliza;
    }

    @Override
    public List<PreparatSanguin> getAll() {

        Transaction tx = null;
        Session session = null;
        List<PreparatSanguin> listOfAllPreparateSanguine = null;

        try{
            session = factory.openSession();
            tx = session.beginTransaction();
            listOfAllPreparateSanguine = session.createQuery("from PreparatSanguin ").list();
            tx.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return listOfAllPreparateSanguine;
    }

}
