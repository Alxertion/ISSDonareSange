package server;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;
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
import javax.persistence.PersistenceException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.PasswordAuthentication;
import java.rmi.RemoteException;
import java.util.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private IRepositoryPreparateSanguine repositoryPreparateSanguine;
    private IRepositoryPacienti repositoryPacienti;
    private IRepositoryCentruTransfuzii repositoryCentruTransfuzii;
    private IRepositorySpitale repositorySpitale;
    Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

    //For remoting
    private Map<String, IObserver> loggedClients;

    public ServerImpl(IRepositoryAnalize repositoryAnalize,
                      IRepositoryCereri repositoryCereri,
                      IRepositoryDonatori repositoryDonatori,
                      IRepositoryGlobuleRosii repositoryGlobuleRosii,
                      IRepositoryMedici repositoryMedici,
                      IRepositoryPersonalTransfuzii repositoryPersonalTransfuzii,
                      IRepositoryPlasma repositoryPlasma,
                      IRepositorySangeNefiltrat repositorySangeNefiltrat,
                      IRepositoryTrombocite repositoryTrombocite,
                      IRepositoryConturi repositoryConturi,
                      IRepositoryPreparateSanguine repositoryPreparateSanguine,
                      IRepositoryCentruTransfuzii repositoryCentruTransfuzii,
                      IRepositorySpitale repositorySpitale,
                      IRepositoryPacienti repositoryPacienti) {
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
        this.repositoryPreparateSanguine=repositoryPreparateSanguine;
        this.repositoryCentruTransfuzii = repositoryCentruTransfuzii;
        this.repositorySpitale = repositorySpitale;
        this.repositoryPacienti = repositoryPacienti;
        loggedClients = new ConcurrentHashMap<>();
    }

    private class MyRunnable implements Runnable{
        private String emailDonator;
        private String continut,mainEmailContinut;
        final String mail="issmailalexertion@gmail.com";
        final String mailPassword="alexertion";
        private Properties props;
        private javax.mail.Session sessionGmail;
        private MailEnum mailEnum;

        public MyRunnable(String emailDonator,String continut,String mainEmailContinut,MailEnum mailEnum){
            this.continut=continut;
            this.emailDonator=emailDonator;
            this.mainEmailContinut=mainEmailContinut;
            this.mailEnum=mailEnum;

            props=getPropertiesConfigEmail(mail);
            sessionGmail=javax.mail.Session.getInstance(props,new GMailAuthenticator(mail,mailPassword));
        }

        public void sendEmail(){
            final String mail="issmailalexertion@gmail.com";
            final String mailPassword="alexertion";
            Properties props=getPropertiesConfigEmail(mail);
            javax.mail.Session sessionGmail;
            sessionGmail=javax.mail.Session.getInstance(props,new GMailAuthenticator(mail,mailPassword));
            //session.setDebug(true);
            try{
                MimeMessage message=new MimeMessage(sessionGmail);
                message.setFrom(new InternetAddress(mail));
                message.addRecipients(javax.mail.Message.RecipientType.TO,InternetAddress.parse("oti_otniel97@yahoo.com"));
                message.setSubject("Rezultate analiza - Centru de transfuzii");


                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(mainEmailContinut);

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                MimeBodyPart attachementBodyPart = new MimeBodyPart();
                DataSource dataSource=new FileDataSource("analiza.txt");
                createResultAnaliza(continut);
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

        public void anuntaDonator(){
            try{
                javax.mail.internet.MimeMessage message=new MimeMessage(sessionGmail);
                message.setFrom(new InternetAddress(mail));
                message.addRecipients(javax.mail.Message.RecipientType.TO,InternetAddress.parse(emailDonator));
                message.setSubject("Nevoie de sange");

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(mainEmailContinut);

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                message.setContent(multipart);

                Transport transport = sessionGmail.getTransport("smtps");
                transport.connect("smtp.gmail.com", 465, mail, mailPassword);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();

            }catch (MessagingException msg){
                throw new RuntimeException(msg);
            }
        }


        @Override
        public void run() {
            if(mailEnum==MailEnum.TRIMITERE_ANALIZA) {
                sendEmail();
            }else if(mailEnum==MailEnum.NOTIFICARE_DONATOR){
                anuntaDonator();
            }
        }
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
    public Donator findDonatorByCNP(String CNP) {
        return repositoryDonatori.findDonatorByCNP(CNP);
    }

    @Override
    public synchronized void login(Cont user,IObserver client,UtilizatorEnum tipUtilizator) throws ServiceException{
        Cont loginOk=repositoryConturi.cautare(user.getUsername());
        if(loggedClients.get(user.getUsername())!=null)
            throw new ServiceException("Acest user este deja logat.");
        //to do
        //in functie de tipul utilizatorului cauti in repositoryul aferent
        //pentru a vedea daca exista un personaj din tipul respectiv cu userul user.getUsername();

        if (loginOk!=null){
            if(tipUtilizator == UtilizatorEnum.MEDIC){
                if(!repositoryMedici.verificaUsername(user.getUsername()))
                    throw new ServiceException("Autentificare esuata");
            }else if(tipUtilizator == UtilizatorEnum.DONATOR){
                if(!repositoryDonatori.verificaUsername(user.getUsername()))
                    throw new ServiceException("Autentificare esuata");
            }else if(tipUtilizator == UtilizatorEnum.PERSONAL_TRANSFUZII){
                if(!repositoryPersonalTransfuzii.verificaUsername(user.getUsername()))
                    throw new ServiceException("Autentificare esuata");
            }
            loggedClients.put(user.getUsername(), client);
        }else
            throw new ServiceException("Autentificare esuata.");
    }

    @Override
    public synchronized void logout(Cont user){
        loggedClients.remove(user.getUsername());
    }

    @Override
    public synchronized void registerAccount(Donator donator) throws ServiceException{
        try {
            repositoryConturi.adaugare(donator.getCont());
            repositoryDonatori.adaugare(donator);
        }catch (PersistenceException e){
            throw new ServiceException("Este deja un cont cu acest nume de utilizator");
        }

    }

    @Override
    public synchronized void recoverPassword(String emailOrUsername) throws ServiceException{
        Matcher mat = pattern.matcher(emailOrUsername);
        Donator don;
        if(mat.matches()){
            // todo de cautat dupa mail
            don=repositoryDonatori.findDonatorByEmail(emailOrUsername);
        }else{
            // caut emailul utilizatorului
            don=repositoryDonatori.findDonatorByUsername(emailOrUsername);
        }
        if(don!=null){
            try {
                String generatedPassword = RandomStringUtils.random(20,true,true);
                don.getCont().setPassword(generatedPassword);
                repositoryConturi.modificare(don.getCont());
                sendRecoverPasswordEmail(don.getEmail(),generatedPassword);
            }catch (IndexOutOfBoundsException|PersistenceException e){
                throw new ServiceException("Va rugam sa verificati emailul/ numele emailului");
            }
        }else{
            throw new ServiceException("Nu am gasit utilizatorul cu acest nume/email");
        }
    }

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


    public void sendRecoverPasswordEmail(String emailDonator, String generatedPassword){
        final String mail="issmailalexertion@gmail.com";
        final String mailPassword="alexertion";
        Properties props=getPropertiesConfigEmail(mail);
        javax.mail.Session sessionGmail;
        sessionGmail=javax.mail.Session.getInstance(props,new GMailAuthenticator(mail,mailPassword));
        //session.setDebug(true);
        try{
            javax.mail.internet.MimeMessage message=new MimeMessage(sessionGmail);
            message.setFrom(new InternetAddress(mail));
            message.addRecipients(javax.mail.Message.RecipientType.TO,InternetAddress.parse(emailDonator));
            message.setSubject("Resetare parola");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Buna,\n\nNoua dumneavoastra parola pentru aplicatia de donare sange este: " +generatedPassword+
                    " .\n Daca nu ati fost dumneavoastra cel care ati cerut resetarea parolei, va rugam sa contactati personalul celui mai apropait centru de transfuzii." +
                    "\n\n O zi frumoasa!");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport transport = sessionGmail.getTransport("smtps");
            transport.connect("smtp.gmail.com", 465, mail, mailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch (MessagingException msg){
            throw new RuntimeException(msg);
        }
    }

    private Properties getPropertiesConfigEmail(String mail) {
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
        return props;
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

    private synchronized void createResultAnaliza(String content){
        try(PrintWriter pw=new PrintWriter(new FileWriter("analiza.txt",false))) {
            pw.println(content);
            pw.close();
        }catch (IOException ioe){
            System.err.println(ioe);
        }
    }

    @Override
    public synchronized void sendEmail(String emailDonator,String continut,String mainEmailContinut,MailEnum mailEnum){
       MyRunnable myRunnableInstance=new MyRunnable(emailDonator,continut,mainEmailContinut,mailEnum);
       Thread t=new Thread(myRunnableInstance);
       t.start();
    }

    @Override
    public void sendSMS(String numarTelefon,String continut) {

        System.out.println("Trimit SMS");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final String ACCOUNT_SID = "ACbf88b891daf48016d21f861edea07d13";
                final String AUTH_TOKEN = "fc328a1711a9f8ed56133b25d1ae0812";


                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message message = Message.creator(
                        new PhoneNumber("+4"+numarTelefon),
                        new PhoneNumber("+13142073815"),
                        continut)
                        .create();

                System.out.println(message.getSid());
            }
        });
        t.start();
    }

    @Override
    public synchronized Donator findDonatorByUsername(String username) {
        return repositoryDonatori.findDonatorByUsername(username);
    }



   @Override
    public synchronized Analiza cautaUltimaAnalizaDupaDonator(int idDonator) {
        PreparatSanguin preparatSanguin=cautaPreparatulSanguinDeTipSangeNefiltratCelMaiRecentAlUnuiDonator(idDonator);
        if(preparatSanguin!=null){
            int idAnaliza=repositoryPreparateSanguine.cautareAnalizaDupaPreparat(preparatSanguin.getIdPreparatSanguin());
            Analiza analiza= repositoryAnalize.cautare(idAnaliza);
            return analiza;
        }
        return null;
    }


    @Override
    public synchronized List<Analiza> cautaAnalizeleUnuiDonator(int idDonator) {
        List<Analiza> listOfAllAnalize = new ArrayList<>();

        List<PreparatSanguin> listOFAllPreparateSanguine = cautaPreparateDupaDonatorSiTip(idDonator, TipPreparatSanguin.SANGE_NEFILTRAT.name());

        for (PreparatSanguin preparatSanguin: listOFAllPreparateSanguine){

            int idAnaliza = repositoryPreparateSanguine.cautareAnalizaDupaPreparat(preparatSanguin.getIdPreparatSanguin());
            Analiza analiza = repositoryAnalize.cautare(idAnaliza);
            if(analiza!=null)
                listOfAllAnalize.add(analiza);
        }

        return listOfAllAnalize;
    }

    @Override
    public int daysBeforeAnotherDonation(int idDonator){

        PreparatSanguin preparatSanguin = cautaPreparatulSanguinDeTipSangeNefiltratCelMaiRecentAlUnuiDonator(idDonator);
        int diff = 180;

        if(preparatSanguin != null){
            org.joda.time.LocalDate now = org.joda.time.LocalDate.now();
            org.joda.time.LocalDate dataPrelevareConvertedToLocalDate = new org.joda.time.LocalDate(preparatSanguin.getDataPrelevare());
            Period period = new Period(dataPrelevareConvertedToLocalDate,now);

           diff = Days.daysBetween(dataPrelevareConvertedToLocalDate, now).getDays();
        }

        return diff;
    }

    @Override
    public synchronized PreparatSanguin cautaPreparatulSanguinDeTipSangeNefiltratCelMaiRecentAlUnuiDonator(int idDonator){
        List<PreparatSanguin> listOfAllPreparateSanguine = cautaPreparateDupaDonatorSiTip(idDonator, TipPreparatSanguin.SANGE_NEFILTRAT.name());
        if(listOfAllPreparateSanguine.size() ==0)
            return null;
        return listOfAllPreparateSanguine.get(0);

    }

    @Override
    public synchronized void stergePreparatSanguinDTO(int idPreparat) {
        repositoryPreparateSanguine.stergere(idPreparat);
        notifyMyClients();
    }

    @Override
    public synchronized void adaugarePreparatSanguinDTO(PreparatSanguinDTO preparatSanguinDTO) {
        PreparatSanguin newPreparatSanguin=new PreparatSanguin(
                preparatSanguinDTO.getDataPrelevare(),
                preparatSanguinDTO.getDataExpirare(),
                preparatSanguinDTO.getCantitate(),
                preparatSanguinDTO.getTip(),
                preparatSanguinDTO.getStagiu()
        );
        repositoryPreparateSanguine.adaugare(newPreparatSanguin);
        //Pacient
        if(preparatSanguinDTO.getIDPacient()!=-1){
            Pacient pacient=repositoryPacienti.cautare(preparatSanguinDTO.getIDPacient());
            pacient.getPreparateSanguine().add(newPreparatSanguin);
            repositoryPacienti.modificare(pacient);
        }
        //Donator
        if(preparatSanguinDTO.getIDDonator()!=-1){
            Donator donator=repositoryDonatori.cautare(preparatSanguinDTO.getIDDonator());
            donator.getPreparateSanguine().add(newPreparatSanguin);
            repositoryDonatori.modificare(donator);
        }
        //Analiza
        if(preparatSanguinDTO.getIDAnaliza()!=-1){
            Analiza analiza=repositoryAnalize.cautare(preparatSanguinDTO.getIDAnaliza());
            analiza.getPreparateSanguine().add(newPreparatSanguin);
            repositoryAnalize.modificare(analiza);
        }
        notifyMyClients();
    }

    @Override
    public void modificaPreparatSanguinDTO( PreparatSanguinDTO preparatSanguinDTO) {
        PreparatSanguin preparatSanguinCurent=repositoryPreparateSanguine.cautare(preparatSanguinDTO.getID());
        PreparatSanguin newPreparatSanguin=new PreparatSanguin(
                preparatSanguinDTO.getDataPrelevare(),
                preparatSanguinDTO.getDataExpirare(),
                preparatSanguinDTO.getCantitate(),
                preparatSanguinDTO.getTip(),
                preparatSanguinDTO.getStagiu()
        );
        newPreparatSanguin.setIdPreparatSanguin(preparatSanguinDTO.getID());
        repositoryPreparateSanguine.modificare(newPreparatSanguin);
        //Pacient
        if(preparatSanguinDTO.getIDPacient()!=-1){
            Pacient pacient=repositoryPacienti.cautare(preparatSanguinDTO.getIDPacient());
            pacient.getPreparateSanguine().add(newPreparatSanguin);
            repositoryPacienti.modificare(pacient);
        }
        //Donator
        if(preparatSanguinDTO.getIDDonator()!=-1){
            Donator donator=repositoryDonatori.cautare(preparatSanguinDTO.getIDDonator());
            donator.getPreparateSanguine().add(newPreparatSanguin);
            repositoryDonatori.modificare(donator);
        }
        //Analiza
        if(preparatSanguinDTO.getIDAnaliza()!=-1){
            Analiza analiza=repositoryAnalize.cautare(preparatSanguinDTO.getIDAnaliza());
            analiza.getPreparateSanguine().add(newPreparatSanguin);
            repositoryAnalize.modificare(analiza);
        }
        notifyMyClients();
    }

    @Override
    public List<PreparatSanguinDTO> getAllPreparatSanguinDTO() {
        List<PreparatSanguin> preparate=repositoryPreparateSanguine.getAll();
        List<PreparatSanguinDTO> list=new ArrayList<>();
        for (PreparatSanguin prep: preparate) {
            int idAnaliza=repositoryPreparateSanguine.cautareAnalizaDupaPreparat(prep.getIdPreparatSanguin());
            int idPacient=repositoryPreparateSanguine.cautarePacientDupaPreparat(prep.getIdPreparatSanguin());
            int idDonator=repositoryPreparateSanguine.findIdDonatorForPreparatSanguin(prep.getIdPreparatSanguin());
            PreparatSanguinDTO newPrepDTO=new PreparatSanguinDTO(
                prep.getIdPreparatSanguin(),
                prep.getTip(),
                prep.getDataPrelevare(),
                prep.getDataExpirare(),
                idDonator,
                idAnaliza,
                prep.getCantitate(),
                idPacient,
                prep.getStagiu()
            );
            list.add(newPrepDTO);
        }
        return list;
    }

    @Override
    public Pacient cautaPacientDupaCNP(String CNP) {
        return repositoryPacienti.cautaPacientDupaCNP(CNP);
    }

    @Override
    public synchronized void inregistreazaDonator(CentruTransfuzii centruTransfuzii, Donator donator, Pacient pacient) {

        centruTransfuzii.getDonatori().add(donator);
        repositoryCentruTransfuzii.modificare(centruTransfuzii);

        adaugaSangeNou();

        List<PreparatSanguin> listCelorMaiRecentePreparateSanguine = repositoryPreparateSanguine.cautareUltimeleNPreparateSanguine(4);
        adaugareSangeNouLaDontor(donator, listCelorMaiRecentePreparateSanguine);

        if(pacient!=null) {
            selecteazaPacientulDorit(pacient, listCelorMaiRecentePreparateSanguine);
        }
    }

    private synchronized void selecteazaPacientulDorit(Pacient pacient, List<PreparatSanguin> listCelorMaiRecentePreparateSanguine) {
        pacient.getPreparateSanguine().addAll(listCelorMaiRecentePreparateSanguine);
        repositoryPacienti.modificare(pacient);
    }

    private synchronized void adaugareSangeNouLaDontor(Donator donator, List<PreparatSanguin> listCelorMaiRecentePreparateSanguine) {
        donator.getPreparateSanguine().addAll(listCelorMaiRecentePreparateSanguine);
        repositoryDonatori.modificare(donator);
    }

    private synchronized void adaugaSangeNou() {

        LocalDate dataRecoltarii1 = LocalDate.now();
        Date dataRecoltarii = java.sql.Date.valueOf(dataRecoltarii1);
        repositoryPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii, null, 400.0, TipPreparatSanguin.SANGE_NEFILTRAT.name(), Stagiu.PRELEVARE.name()));
        repositoryPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii, null, 100.0, TipPreparatSanguin.TROMBOCITE.name(), Stagiu.PRELEVARE.name()));
        repositoryPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii, null, 100.0, TipPreparatSanguin.GLOBULE_ROSII.name(), Stagiu.PRELEVARE.name()));
        repositoryPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii, null, 200.0, TipPreparatSanguin.PLASMA.name(), Stagiu.PRELEVARE.name()));
    }

    @Override
    public CentruTransfuzii cautaCelMaiApropiatCentruDeTransfuzii() {

        CentruTransfuzii centruTransfuzii = repositoryCentruTransfuzii.cautare(1);

        return centruTransfuzii;
    }

    private List<PreparatSanguin> cautaPreparateleSanguineCeleMaiRecenteAleUnuiDonator(int idDonator){
        List<PreparatSanguin> listOfPreparate=new ArrayList<>();
        listOfPreparate.add((PreparatSanguin) cautaPreparateDupaDonatorSiTip(idDonator,TipPreparatSanguin.GLOBULE_ROSII.name()).get(0));
        listOfPreparate.add((PreparatSanguin) cautaPreparateDupaDonatorSiTip(idDonator,TipPreparatSanguin.TROMBOCITE.name()).get(0));
        listOfPreparate.add((PreparatSanguin) cautaPreparateDupaDonatorSiTip(idDonator,TipPreparatSanguin.PLASMA.name()).get(0));
        return listOfPreparate;
    }

    private void stergePreparateVechiDonator(int idDonator){
        List<PreparatSanguin> preparatSanguinList=cautaPreparateDupaDonatorSiTip(idDonator,TipPreparatSanguin.SANGE_NEFILTRAT.name());
        if(preparatSanguinList.size()==3) {
            repositoryPreparateSanguine.stergere(preparatSanguinList.get(2).getIdPreparatSanguin());
            preparatSanguinList = cautaPreparateDupaDonatorSiTip(idDonator, TipPreparatSanguin.GLOBULE_ROSII.name());
            repositoryPreparateSanguine.stergere(preparatSanguinList.get(2).getIdPreparatSanguin());
            preparatSanguinList = cautaPreparateDupaDonatorSiTip(idDonator, TipPreparatSanguin.PLASMA.name());
            repositoryPreparateSanguine.stergere(preparatSanguinList.get(2).getIdPreparatSanguin());
            preparatSanguinList = cautaPreparateDupaDonatorSiTip(idDonator, TipPreparatSanguin.TROMBOCITE.name());
            repositoryPreparateSanguine.stergere(preparatSanguinList.get(2).getIdPreparatSanguin());
            preparatSanguinList = cautaPreparateDupaDonatorSiTip(idDonator, TipPreparatSanguin.SANGE_NEFILTRAT.name());
            repositoryPreparateSanguine.stergere(preparatSanguinList.get(2).getIdPreparatSanguin());
        }
    }

    private Analiza cautaUltimaAnalizaAdaugata(){
        List<Analiza> toateAnalizele=repositoryAnalize.getAll();
        return toateAnalizele.get(toateAnalizele.size()-1);
    }

    @Override
    public synchronized void adaugaAnalizaLaDonator(int idDonator, Analiza analiza) throws ServiceException{
        PreparatSanguin preparatSanguin=cautaPreparatulSanguinDeTipSangeNefiltratCelMaiRecentAlUnuiDonator(idDonator);
        if(repositoryPreparateSanguine.cautareAnalizaDupaPreparat(preparatSanguin.getIdPreparatSanguin())!=-1)
            throw new ServiceException("Nu exista sange apartinand acestui donator pentru a i se atribui analiza");
        stergePreparateVechiDonator(idDonator);
        repositoryAnalize.adaugare(analiza);
        Analiza analiza1=cautaUltimaAnalizaAdaugata();
        analiza1.getPreparateSanguine().add(preparatSanguin);
        repositoryAnalize.modificare(analiza1);
    }

    private synchronized List<PreparatSanguin> cautaPreparateDupaDonatorSiTip(int idDonator, String tipPreparatSanguin) {
        Donator donator=repositoryDonatori.cautare(idDonator);
        List<PreparatSanguin> listOfAllPreparateSanguine = donator.getPreparateSanguine();

        if(listOfAllPreparateSanguine.size() >0){
            listOfAllPreparateSanguine=listOfAllPreparateSanguine.stream().filter(x->{
                return x.getTip().equals(tipPreparatSanguin);
            }).sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        }

        return listOfAllPreparateSanguine;
    }

    @Override
    public synchronized List<Spital> getSpitale() {
        return repositorySpitale.getAll();
    }

    @Override
    public synchronized List<CentruTransfuzii> getCentreTransfuzii() {
        return repositoryCentruTransfuzii.getAll();
    }

    @Override
    public synchronized void adaugaSpital(Spital spital) {
        repositorySpitale.adaugare(spital);
    }

    @Override
    public synchronized void adaugaCentruTransfuzii(CentruTransfuzii centruTransfuzii) {
        repositoryCentruTransfuzii.adaugare(centruTransfuzii);
    }

    @Override
    public synchronized void modificaSpital(Spital spital) {
        repositorySpitale.modificare(spital);
    }

    @Override
    public synchronized void modificaCentruTransfuzii(CentruTransfuzii centruTransfuzii) {
        repositoryCentruTransfuzii.modificare(centruTransfuzii);
    }

    @Override
    public synchronized void stergeSpital(int id) {
        repositorySpitale.stergere(id);
    }

    @Override
    public synchronized void stergeCentruTransfuzii(int id) {
        repositoryCentruTransfuzii.stergere(id);
    }

    @Override
    public synchronized void adaugaMedic(Medic medic, int idSpital) {
        Spital s = repositorySpitale.cautare(idSpital);
        s.getMedici().add(medic);
        repositorySpitale.modificare(s);
    }

    @Override
    public synchronized void stergeMedic(int id) {
        repositoryMedici.stergere(id);
    }

    @Override
    public synchronized void adaugaPersonalTransfuzii(PersonalTransfuzii personalTransfuzii, int idCentruTransfuzii) {
        CentruTransfuzii c = repositoryCentruTransfuzii.cautare(idCentruTransfuzii);
        c.getPersonalTransfuzii().add(personalTransfuzii);
        repositoryCentruTransfuzii.modificare(c);
    }

    @Override
    public synchronized void stergePersonalTransfuzii(int id) {
        repositoryPersonalTransfuzii.stergere(id);
    }

    @Override
    public synchronized List<Cerere> getCereri() {
        return repositoryCereri.getAll();
    }

    @Override
    public synchronized void stergeCerere(Cerere cerere) {
        repositoryCereri.stergere(cerere.getIdCerere());
    }

    @Override
    public synchronized void schimbaParolaMedic(String username, String parolaCurenta, String parolaNoua) throws Exception {
        Cont contCurent = repositoryConturi.cautare(username);
        if (Objects.equals(contCurent.getPassword(), parolaCurenta)) {
            contCurent.setPassword(parolaNoua);
            repositoryConturi.modificare(contCurent);
        }
        else {
            throw new Exception("Parola curenta e gresita!");
        }
    }

    @Override
    public synchronized String getNumeMedic(Cont cont) {
        for (Medic m : repositoryMedici.getAll())
            if (Objects.equals(m.getCont().getUsername(), cont.getUsername()))
                return m.getNume() + " " + m.getPrenume();
        return "-";
    }

    @Override
    public synchronized List<Pacient> getPacienti() {
        return repositoryPacienti.getAll();
    }

    @Override
    public synchronized void adaugaCerere(String usernameMedic, String cnpPacient, String numePacient, String prenumePacient, Prioritate prioritate, String grupa, Boolean RH, Double cantitateCeruta, Double cantitateActuala, java.util.Date dataEfectuare, String tipSange) {
        // cream cererea
        Cerere cerere = new Cerere(prioritate, grupa, RH, cantitateCeruta, cantitateActuala, dataEfectuare, tipSange);
        int maxIdCerere = 0;
        for (Cerere c : repositoryCereri.getAll()) {
            if (c.getIdCerere() > maxIdCerere)
                maxIdCerere = c.getIdCerere();
        }
        maxIdCerere++;
        cerere.setIdCerere(maxIdCerere);

        // cautam pacientul
        int maxIdPacient = 0;
        Pacient pacient = null;
        for (Pacient p : repositoryPacienti.getAll()) {
            if (Objects.equals(p.getCnp(), cnpPacient))
                pacient = p;
            if (p.getIdPacient() > maxIdPacient)
                maxIdPacient = p.getIdPacient();
        }
        maxIdPacient++;

        // daca nu gasim pacientul, il cream
        if (pacient == null) {
            pacient = new Pacient(cnpPacient, numePacient, prenumePacient);
            repositoryPacienti.adaugare(pacient);
            pacient = repositoryPacienti.cautare(maxIdPacient);
        }

        // cautam medicul
        Medic medic = null;
        for (Medic m : repositoryMedici.getAll()) {
            if (Objects.equals(m.getUsername(), usernameMedic)) {
                medic = m;
            }
        }

        medic.getCereri().add(cerere);
        pacient.getCereri().add(cerere);
        repositoryMedici.modificare(medic);
        repositoryPacienti.modificare(pacient);
    }

    @Override
    public synchronized int getIdSpital(Medic medic) {
        for (Spital s : repositorySpitale.getAll()) {
            for (Medic m : s.getMedici()) {
                if (m.getIdMedic() == medic.getIdMedic())
                    return s.getIdSpital();
            }
        }
        return -1;
    }

    @Override
    public synchronized int getIdCentruTransfuzii(PersonalTransfuzii personalTransfuzii) {
        for (CentruTransfuzii c : repositoryCentruTransfuzii.getAll()) {
            for (PersonalTransfuzii p : c.getPersonalTransfuzii()) {
                if (p.getIdPersonalTransfuzii() == personalTransfuzii.getIdPersonalTransfuzii())
                    return c.getIdCentruTransfuzii();
            }
        }
        return -1;
    }

    @Override
    public synchronized List<PreparatSanguin> getPreparateSanguine() {
        return repositoryPreparateSanguine.getAll();
    }

    @Override
    public List<Analiza> getAnalize() {
        return repositoryAnalize.getAll();
    }

    @Override
    public void updatePreparatSanguin(PreparatSanguin p) {
        repositoryPreparateSanguine.modificare(p);
    }

    @Override
    public void updatePacient(Pacient p) {
        repositoryPacienti.modificare(p);
    }

    @Override
    public void updateCerere(Cerere c) {
        repositoryCereri.modificare(c);
    }

    @Override
    public PersonalTransfuzii getPersonalTransfuzieDupaCont(Cont user) {
        List<PersonalTransfuzii> personalTransfuziiList=getPersonalTransfuzii();
        for(PersonalTransfuzii personalTransfuzii : personalTransfuziiList){
            if(personalTransfuzii.getCont().getUsername().equals(user.getUsername())){
                return personalTransfuzii;
            }
        }
        return null;
    }

    private Double determinaDistanta(CentruTransfuzii centruTransfuzii,Donator donator){
        double sumOfDiff=Math.pow(centruTransfuzii.getLongitudine()-donator.getLongitudine(),2)+Math.pow(centruTransfuzii.getLatitudine()-donator.getLatitudine(),2);
        return Math.sqrt(sumOfDiff);
    }

    @Override
    public Donator getCelMaiApropiatDonator(Integer idCentruTransfuzii,List<Donator> totiDonatorii) {
        CentruTransfuzii centruTransfuzii=repositoryCentruTransfuzii.cautare(idCentruTransfuzii);
        Donator celMaiApropiat=null;
        Double distanta=-1.0;
        for(Donator donator:totiDonatorii) {
            if (donator.getLatitudine() != null && donator.getLongitudine() != null) {
                if (distanta == -1.0) {
                    distanta = determinaDistanta(centruTransfuzii, donator);
                    celMaiApropiat = donator;
                } else {
                    double distantaCurenta = determinaDistanta(centruTransfuzii, donator);
                    if (distanta < distantaCurenta) {
                        distanta = distantaCurenta;
                        celMaiApropiat = donator;
                    }
                }
            }
        }
        return celMaiApropiat;
    }

    @Override
    public List<Donator> cautaDonatoriDupaDistanta(Double distanta,String grupa,String rh,Integer idCentruTransfuzie) {
        List<Donator> donatorList;
        List<Donator> result=new ArrayList<>();
        if(!grupa.equals("")){
            donatorList=cautaDonatoriCompatibili(grupa,rh);
        }else{
            donatorList=repositoryDonatori.getAll();
        }
        CentruTransfuzii centruTransfuzii=repositoryCentruTransfuzii.cautare(idCentruTransfuzie);
        for(Donator donator:donatorList) {
            if (donator.getLatitudine() != null && donator.getLongitudine() != null) {
                double distantaCurenta = determinaDistanta(centruTransfuzii, donator);
                if(distantaCurenta<=distanta)
                    result.add(donator);
            }
        }
        return result;
    }

    private boolean verificaDonatorByCerere(String grupa,String rh,Analiza analiza){
        boolean RH= rh.equals("-") ? false : true;
        if(grupa.equals("A(II)") && RH==false){
            if((analiza.getGrupa().equals("A(II)") || analiza.getGrupa().equals("O(I)")) && analiza.getRH()==false){
                return true;
            }
        }else if(grupa.equals("A(II)") && RH==true){
            if((analiza.getGrupa().equals("A(II)") || analiza.getGrupa().equals("O(I)"))){
                return true;
            }
        }else if(grupa.equals("B(III)") && RH==false){
            if((analiza.getGrupa().equals("B(III)") || analiza.getGrupa().equals("O(I)")) && analiza.getRH()==false){
                return true;
            }
        }else if(grupa.equals("B(III)") && RH==true){
            if((analiza.getGrupa().equals("B(III)") || analiza.getGrupa().equals("O(I)"))){
                return true;
            }
        }else if(grupa.equals("AB(IV)") && RH==true){
            return true;
        }else if(grupa.equals("AB(IV)") && RH==false){
            if(analiza.getRH()==false)
                return true;
        }else if(grupa.equals("O(I)") && RH==true){
            if(analiza.getGrupa().equals("O(I)"))
                return true;
        }else if(grupa.equals("O(I)") && RH==false){
            if(analiza.getGrupa().equals("O(I)") && analiza.getRH()==false){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Donator> cautaDonatoriCompatibili(String grupa, String rh) {
        List<Donator> totiDonatorii=repositoryDonatori.getAll();
        List<Donator> ceiCompatibili=new ArrayList<>();
        for(Donator donator:totiDonatorii){
            Analiza analizaDonator=cautaUltimaAnalizaDupaDonator(donator.getIdDonator());
            if(analizaDonator!=null) {
                if (analizaDonator.getBoli().size() == 0) {
                    if(verificaDonatorByCerere(grupa,rh,analizaDonator))
                        ceiCompatibili.add(donator);
                }
            }
        }
        return ceiCompatibili;
    }
}
