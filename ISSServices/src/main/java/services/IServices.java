package services;

import model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface IServices {
    void login(Cont user, IObserver client) throws ServiceException;
    void logout(Cont user);
    List<Medic> getMedici();
    List<PersonalTransfuzii> getPersonalTransfuzii();
    List<Donator> getDonatori();
    void sendEmail(String emailDonatosr,String continut);
    Donator findDonatorByUsername(String username);
    Analiza cautaUltimaAnalizaDupaDonator(int idDonator);
    List<Analiza> cautaAnalizeleUnuiDonator(int idDonator);
    PreparatSanguin cautaPreparatulSanguinDeTipSangeNefiltratCelMaiRecentAlUnuiDonar(int idDonator);
    Pacient cautaPacientDupaCNP(String CNP);
    void inregistreazaDonator(CentruTransfuzii centruTransfuzii, Donator donator, Pacient pacient);
    void updateDonator(Donator donator, String numeDonator, String prenumeDonator, String telefon);
    CentruTransfuzii cautaCelMaiApropiatCentruDeTransfuzii();
}
