import hello.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;

import java.util.Iterator;
import java.util.List;

/**
 * Created by grigo on 4/23/17.
 */
public class MessageMain {
    //INSERT
    void addMessage(){
        Session session = sessionFactory.openSession();
        Transaction tx=null;
        try{
            tx = session.beginTransaction();
            Message message = new Message("New Hello World www");
            session.save(message);
            tx.commit();
        }catch(RuntimeException ex){
            if (tx!=null)
                tx.rollback();
        }finally{
            session.close();
        }
    }

    void updateMessage(){
        Session session = sessionFactory.openSession();
        Transaction tx=null;
        try{
            tx = session.beginTransaction();
            Message message =
                    (Message) session.load( Message.class, new Long(23) );
            message.setText("New Text 43");
            Message nextMessage = new Message("Next message yuii");
            message.setNextMessage( nextMessage );
            //session.update(message);
            tx.commit();

        } catch(RuntimeException ex){
            if (tx!=null)
                tx.rollback();
        }finally{
            session.close();
        }
    }

    //DELETE
    void deleteMessage(){
        Session session = sessionFactory.openSession();
        Transaction tx=null;
        try{
            tx = session.beginTransaction();

            Message crit= session.createQuery("from Message where text like 'Ne%'", Message.class)
                    .setMaxResults(1)
                    .uniqueResult();
            System.err.println("Stergem mesajul "+crit.getId());
            session.delete(crit);
            tx.commit();
        } catch(RuntimeException ex){
            if (tx!=null)
                tx.rollback();
        }finally{
            session.close();
        }
    }

    //SELECT
    void getMessages(){
        Session session = sessionFactory.openSession();

        Transaction tx=null;
        try{
            tx = session.beginTransaction();
            List<Message> messages =
                    session.createQuery("from Message as m order by m.text asc",Message.class).
                          //  setFirstResult(1).setMaxResults(5).
            list();
            System.out.println( messages.size() + " message(s) found:" );
            for (Message m:messages ) {
                System.out.println(  m.getText()+' '+m.getId() );
            }
            tx.commit();
        }catch(RuntimeException ex){
            if (tx!=null)
                tx.rollback();
        }finally{
            session.close();
        }
    }

    //static SessionFactory sessions;
    public static void main(String[] args) {
        try {
            initialize();

            MessageMain test = new MessageMain();
            test.addMessage();
            test.getMessages();
            test.updateMessage();
           // test.deleteMessage();
            test.getMessages();
        }catch (Exception e){
            System.err.println("Exception "+e);
            e.printStackTrace();
        }finally {
            close();
        }
    }


    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

}
