package services;

import model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;


public interface IServices {
    void login(Cont user, IObserver client,UtilizatorEnum tipUtilizator) throws ServiceException;
    Donator findDonatorByCNP(String CNP);
    void logout(Cont user);
    void recoverPassword(String string) throws ServiceException;
    void registerAccount(Donator donator) throws ServiceException;
    List<Medic> getMedici();
    List<PersonalTransfuzii> getPersonalTransfuzii();
    List<Donator> getDonatori();
    void sendEmail(String emailDonatosr,String continut,String mainEmailContinut,MailEnum mailEnum);
    void sendSMS(String numarTelefon,String continut);
    Donator findDonatorByUsername(String username);
    Analiza cautaUltimaAnalizaDupaDonator(int idDonator);
    List<Analiza> cautaAnalizeleUnuiDonator(int idDonator);
    PreparatSanguin cautaPreparatulSanguinDeTipSangeNefiltratCelMaiRecentAlUnuiDonator(int idDonator);
    void stergePreparatSanguinDTO(int idPreparat);
    void adaugarePreparatSanguinDTO(PreparatSanguinDTO preparatSanguin);
    void modificaPreparatSanguinDTO(PreparatSanguinDTO preparatSanguinNou);
    List<PreparatSanguinDTO> getAllPreparatSanguinDTO();

    void adaugaAnalizaLaDonator(int idDonator,Analiza analiza) throws ServiceException;
    List<Spital> getSpitale();
    List<CentruTransfuzii> getCentreTransfuzii();
    void adaugaSpital(Spital spital);
    void adaugaCentruTransfuzii(CentruTransfuzii centruTransfuzii);
    void modificaSpital(Spital spital);
    void modificaCentruTransfuzii(CentruTransfuzii centruTransfuzii);
    void stergeSpital(int id);
    void stergeCentruTransfuzii(int id);
    void adaugaMedic(Medic medic, int idSpital);
    void stergeMedic(int id);
    void adaugaPersonalTransfuzii(PersonalTransfuzii personalTransfuzii, int idCentruTransfuzii);
    void stergePersonalTransfuzii(int id);
    Pacient cautaPacientDupaCNP(String CNP);
    void inregistreazaDonator(CentruTransfuzii centruTransfuzii, Donator donator, Pacient pacient);
    CentruTransfuzii cautaCelMaiApropiatCentruDeTransfuzii();
    List<Cerere> getCereri();
    void stergeCerere(Cerere cerere);
    void schimbaParolaMedic(String username, String parolaCurenta, String parolaNoua) throws Exception;
    String getNumeMedic(Cont cont);
    List<Pacient> getPacienti();
    int daysBeforeAnotherDonation(int idDonator);
    List<Donator> cautaDonatoriCompatibili(String grupa,String rh);
    void adaugaCerere(String usernameMedic, String cnpPacient, String numePacient, String prenumePacient, Prioritate prioritate, String grupa, Boolean RH, Double cantitateCeruta, Double cantitateActuala, Date dataEfectuare, String tipSange);

    int getIdSpital(Medic medic);
    int getIdCentruTransfuzii(PersonalTransfuzii personalTransfuzii);
    List<PreparatSanguin> getPreparateSanguine();
    List<Analiza> getAnalize();
    void updatePreparatSanguin(PreparatSanguin p);
    void updatePacient(Pacient p);
    void updateCerere(Cerere c);
    PersonalTransfuzii getPersonalTransfuzieDupaCont(Cont user);
    Donator getCelMaiApropiatDonator(Integer idCentruTransfuzii,List<Donator> totiDonatorii);
    List<Donator> cautaDonatoriDupaDistanta(Double distanta,String grupa,String rh,Integer idCentruTransfuzie);
}
