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
    Analiza cautaAnalizaDupaDonator(int idDonator);
}
