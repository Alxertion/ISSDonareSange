package server;

import model.*;
import persistence.repository.*;
import services.IObserver;
import services.IServices;
import services.ServiceException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.PasswordAuthentication;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImpl implements IServices {
    private IRepositoryAnalize repositoryAnalize;
    private IRepositoryCereri repositoryCereri;
    private IRepositoryDonatori repositoryDonatori;
    private IRepositoryGlobuleRosii repositoryGlobuleRosii;
    private IRepositoryMedici repositoryMedici;
    private IRepositoryPersonalTransfuzii repositoryPersonalTransfuzii;
    private IRepositoryPlasma repositoryPlasma;
    private IRepositorySangeNefiltrat repositorySangeNefiltrat;
    private IRepositoryTrombocite repositoryTrombocite;
    private IRepositoryConturi repositoryConturi;

    //For remoting
    private Map<String, IObserver> loggedClients;

    public ServerImpl(IRepositoryAnalize repositoryAnalize, IRepositoryCereri repositoryCereri, IRepositoryDonatori repositoryDonatori, IRepositoryGlobuleRosii repositoryGlobuleRosii, IRepositoryMedici repositoryMedici,IRepositoryPersonalTransfuzii repositoryPersonalTransfuzii,IRepositoryPlasma repositoryPlasma,IRepositorySangeNefiltrat repositorySangeNefiltrat,IRepositoryTrombocite repositoryTrombocite,IRepositoryConturi repositoryConturi){
        this.repositoryAnalize=repositoryAnalize;
        this.repositoryCereri=repositoryCereri;
        this.repositoryDonatori=repositoryDonatori;
        this.repositoryGlobuleRosii=repositoryGlobuleRosii;
        this.repositoryMedici=repositoryMedici;
        this.repositoryPersonalTransfuzii=repositoryPersonalTransfuzii;
        this.repositoryPlasma=repositoryPlasma;
        this.repositorySangeNefiltrat=repositorySangeNefiltrat;
        this.repositoryTrombocite=repositoryTrombocite;
        this.repositoryConturi=repositoryConturi;
        loggedClients=new ConcurrentHashMap<>();
    }

    private void notifyMyClients(){
        ExecutorService executor= Executors.newFixedThreadPool(loggedClients.size());
        for(IObserver client:loggedClients.values()){
            if (client!=null)
                executor.execute(() -> {
                    try {
                        client.notifyClient();
                    } catch (RemoteException e) {
                        System.out.println("Error notifyClients server side " + e);
                    }
                });
        }
        executor.shutdown();
    }

    @Override
    public synchronized void login(Cont user,IObserver client) throws ServiceException{
        Cont loginOk=repositoryConturi.cautare(user.getUsername());
        if (loginOk!=null){
            if(loggedClients.get(user.getUsername())!=null)
                throw new ServiceException("Acest user este deja logat.");
            loggedClients.put(user.getUsername(), client);
        }else
            throw new ServiceException("Autentificare esuata.");
    }

    @Override
    public synchronized void logout(Cont user){
        loggedClients.remove(user.getUsername());
    }

    @Override
    public synchronized List<Medic> getMedici(){
        return repositoryMedici.getAll();
    }

    @Override
    public synchronized List<PersonalTransfuzii> getPersonalTransfuzii() {
        return repositoryPersonalTransfuzii.getAll();
    }

    @Override
    public synchronized List<Donator> getDonatori(){
        List<Donator> donators=repositoryDonatori.getAll();
        return donators;
    }

    private void createResultAnaliza(String content){
        try(PrintWriter pw=new PrintWriter(new FileWriter("analiza.txt",false))) {
            pw.println(content);
            pw.close();
        }catch (IOException ioe){
            System.err.println(ioe);
        }
    }

    @Override
    public synchronized void sendEmail(String emailDonator,String continut){
        class GMailAuthenticator extends javax.mail.Authenticator {
            String issEmail;
            String issPasword;
            public GMailAuthenticator (String username, String password)
            {
                super();
                this.issEmail = username;
                this.issPasword = password;
            }
            public javax.mail.PasswordAuthentication getPasswordAuthentication()
            {
                return new javax.mail.PasswordAuthentication(issEmail, issPasword);
            }
        }

        final String mail="issmailalexertion@gmail.com";
        final String mailPassword="alexertion";
        Properties props=new Properties();
        props.put("mail.smtp.user", mail);
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        javax.mail.Session sessionGmail;
        sessionGmail=javax.mail.Session.getInstance(props,new GMailAuthenticator(mail,mailPassword));
        //session.setDebug(true);
        try{
            MimeMessage message=new MimeMessage(sessionGmail);
            message.setFrom(new InternetAddress(mail));
            message.addRecipients(Message.RecipientType.TO,InternetAddress.parse("oti_otniel97@yahoo.com"));
            message.setSubject("Rezultate analiza - Centru de transfuzii");


            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Buna ziua,\nAveti atasat acestui mail un fisier cu rezultatele" +
                    " analizelor dumneavoastra.\n\nVa dorim o zi placuta!");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            MimeBodyPart attachementBodyPart = new MimeBodyPart();
            DataSource dataSource=new FileDataSource("analiza.txt");
            createResultAnaliza("Hai ca e ok");
            attachementBodyPart.setDataHandler(new DataHandler(dataSource));
            attachementBodyPart.setFileName("analiza.txt");
            multipart.addBodyPart(attachementBodyPart);

            message.setContent(multipart);

            Transport transport = sessionGmail.getTransport("smtps");
            transport.connect("smtp.gmail.com", 465, mail, mailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        }catch (MessagingException msg){
            throw new RuntimeException(msg);
        }
    }
}
