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
import java.io.Serializable;
import java.net.PasswordAuthentication;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private IRepositoryCentruTransfuzii repositoryCentruTransfuzii;
    private IRepositorySpitale repositorySpitale;
    private IRepositoryPacienti repositoryPacienti;

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
        this.repositoryAnalize = repositoryAnalize;
        this.repositoryCereri = repositoryCereri;
        this.repositoryDonatori = repositoryDonatori;
        this.repositoryGlobuleRosii = repositoryGlobuleRosii;
        this.repositoryMedici = repositoryMedici;
        this.repositoryPersonalTransfuzii = repositoryPersonalTransfuzii;
        this.repositoryPlasma = repositoryPlasma;
        this.repositorySangeNefiltrat = repositorySangeNefiltrat;
        this.repositoryTrombocite = repositoryTrombocite;
        this.repositoryConturi = repositoryConturi;
        this.repositoryPreparateSanguine = repositoryPreparateSanguine;
        this.repositoryCentruTransfuzii = repositoryCentruTransfuzii;
        this.repositorySpitale = repositorySpitale;
        this.repositoryPacienti = repositoryPacienti;
        loggedClients = new ConcurrentHashMap<>();
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
            message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(emailDonator));
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
    public synchronized Donator findDonatorByUsername(String username) {
        return repositoryDonatori.findDonatorByUsername(username);
    }



   @Override
    public synchronized Analiza cautaUltimaAnalizaDupaDonator(int idDonator) {
        PreparatSanguin preparatSanguin=cautaPreparatulSanguinDeTipSangeNefiltratCelMaiRecentAlUnuiDonar(idDonator);
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
    public synchronized PreparatSanguin cautaPreparatulSanguinDeTipSangeNefiltratCelMaiRecentAlUnuiDonar(int idDonator){
        List<PreparatSanguin> listOfAllPreparateSanguine = cautaPreparateDupaDonatorSiTip(idDonator, TipPreparatSanguin.SANGE_NEFILTRAT.name());
        return listOfAllPreparateSanguine.get(0);

    }

    private List<PreparatSanguin> cautaPreparateleSanguineCeleMaiRecenteAleUnuiDonator(int idDonator){
        List<PreparatSanguin> listOfPreparate=new ArrayList<>();
        listOfPreparate.add((PreparatSanguin) cautaPreparateDupaDonatorSiTip(idDonator,TipPreparatSanguin.GLOBULE_ROSII.name()).get(0));
        listOfPreparate.add((PreparatSanguin) cautaPreparateDupaDonatorSiTip(idDonator,TipPreparatSanguin.TROMBOCITE.name()).get(0));
        listOfPreparate.add((PreparatSanguin) cautaPreparateDupaDonatorSiTip(idDonator,TipPreparatSanguin.PLASMA.name()).get(0));
        return listOfPreparate;
    }

    private void stergePreparateDonator(int idDonator){
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

    @Override
    public synchronized void adaugaAnalizaLaDonator(int idDonator, Analiza analiza) throws ServiceException{
        PreparatSanguin preparatSanguin=cautaPreparatulSanguinDeTipSangeNefiltratCelMaiRecentAlUnuiDonar(idDonator);
        if(repositoryPreparateSanguine.cautareAnalizaDupaPreparat(preparatSanguin.getIdPreparatSanguin())==-1)
            throw new ServiceException("Nu exista sange apartinand acestui donator pentru a i se atribui analiza");
        stergePreparateDonator(idDonator);
        analiza.getPreparateSanguine().add(preparatSanguin);
        for (PreparatSanguin preparatSanguin1:cautaPreparateleSanguineCeleMaiRecenteAleUnuiDonator(idDonator)){
            analiza.getPreparateSanguine().add(preparatSanguin1);
        }
        System.out.println("");
        repositoryAnalize.adaugare(analiza);
    }

    private synchronized List<PreparatSanguin> cautaPreparateDupaDonatorSiTip(int idDonator, String tipPreparatSanguin) {
        Donator donator=repositoryDonatori.cautare(idDonator);
        List<PreparatSanguin> listOfAllPreparateSanguine = donator.getPreparateSanguine();

        if(listOfAllPreparateSanguine.size() >0){
            listOfAllPreparateSanguine=listOfAllPreparateSanguine.stream().filter(x->{
                return x.getTip().equals(tipPreparatSanguin);
            }).sorted(Comparator.comparing(PreparatSanguin::getDataPrelevare)).collect(Collectors.toList());
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
    public synchronized void adaugaMedic(Medic medic) {
        repositoryMedici.adaugare(medic);
    }

    @Override
    public synchronized void stergeMedic(int id) {
        repositoryMedici.stergere(id);
    }

    @Override
    public synchronized void adaugaPersonalTransfuzii(PersonalTransfuzii personalTransfuzii) {
        repositoryPersonalTransfuzii.adaugare(personalTransfuzii);
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
    public List<Pacient> getPacienti() {
        return repositoryPacienti.getAll();
    }

    @Override
    public void adaugaCerere(String usernameMedic, String cnpPacient, String numePacient, String prenumePacient, Prioritate prioritate, String grupa, Boolean RH, Double cantitateCeruta, Double cantitateActuala, Date dataEfectuare) {
        // cream cererea
        Cerere cerere = new Cerere(prioritate, grupa, RH, cantitateCeruta, cantitateActuala, dataEfectuare);
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
            pacient = new Pacient(maxIdPacient, cnpPacient, numePacient, prenumePacient);
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
}
