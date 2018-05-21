package server;

import model.*;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.sql.Date;
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
        private String continut;
        public MyRunnable(String emailDonator,String continut){
            this.continut=continut;
            this.emailDonator=emailDonator;
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
                message.addRecipients(Message.RecipientType.TO,InternetAddress.parse("oti_otniel97@yahoo.com"));
                message.setSubject("Rezultate analiza - Centru de transfuzii");


                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText("Buna ziua,\nAveti atasat acestui mail un fisier cu rezultatele" +
                        " analizelor dumneavoastra.\n\nVa dorim o zi placuta!");

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


        @Override
        public void run() {
            sendEmail();
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
            MimeMessage message=new MimeMessage(sessionGmail);
            message.setFrom(new InternetAddress(mail));
            message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(emailDonator));
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
    public synchronized void sendEmail(String emailDonator,String continut){
       MyRunnable myRunnableInstance=new MyRunnable(emailDonator,continut);
       Thread t=new Thread(myRunnableInstance);
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
        List<Integer> listOfallIds = new ArrayList<>();

        List<PreparatSanguin> listOFAllPreparateSanguine = cautaPreparateDupaDonatorSiTip(idDonator, TipPreparatSanguin.SANGE_NEFILTRAT.name());
        listOFAllPreparateSanguine.forEach(preparatSanguin -> listOfallIds.add(
                repositoryPreparateSanguine.cautareAnalizaDupaPreparat(preparatSanguin.getIdPreparatSanguin())
        ));

        listOfallIds.forEach(idAnaliza -> listOfAllAnalize.add(repositoryAnalize.cautare(idAnaliza)));

        return listOfAllAnalize;
    }

    @Override
    public synchronized PreparatSanguin cautaPreparatulSanguinDeTipSangeNefiltratCelMaiRecentAlUnuiDonator(int idDonator){
        List<PreparatSanguin> listOfAllPreparateSanguine = cautaPreparateDupaDonatorSiTip(idDonator, TipPreparatSanguin.SANGE_NEFILTRAT.name());
        return listOfAllPreparateSanguine.get(0);

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
        Date dataRecoltarii = Date.valueOf(dataRecoltarii1);
        repositoryPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii, null, 400.0, TipPreparatSanguin.SANGE_NEFILTRAT.name(), Stagiu.PRELEVARE.name()));
        repositoryPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii, null, 100.0, TipPreparatSanguin.TROMBOCITE.name(), Stagiu.PRELEVARE.name()));
        repositoryPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii, null, 100.0, TipPreparatSanguin.GLOBULE_ROSII.name(), Stagiu.PRELEVARE.name()));
        repositoryPreparateSanguine.adaugare(new PreparatSanguin(dataRecoltarii, null, 200.0, TipPreparatSanguin.PLASMA.name(), Stagiu.PRELEVARE.name()));
    }

    @Override
    public synchronized void updateDonator(Donator donator, String numeDonator, String prenumeDonator, String telefon) {

        donator.setNume(numeDonator);
        donator.setPrenume(prenumeDonator);
        donator.setTelefon(telefon);
        repositoryDonatori.modificare(donator);


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
}
