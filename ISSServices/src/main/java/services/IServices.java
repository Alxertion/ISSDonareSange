package services;

import model.*;

import java.util.List;


public interface IServices {
    void login(Cont user, IObserver client) throws ServiceException;
    void logout(Cont user);
    List<Medic> getMedici();
    List<PersonalTransfuzii> getPersonalTransfuzii();
    List<Donator> getDonatori();
}
