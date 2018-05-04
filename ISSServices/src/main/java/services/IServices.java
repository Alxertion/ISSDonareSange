package services;

import model.Cont;
import model.Medic;
import model.PersonalTransfuzii;
import model.Spital;


public interface IServices {
    void login(Cont user, IObserver client) throws ServiceException;
    void logout(Cont user);
    Iterable<Medic> getMedici();
    Iterable<PersonalTransfuzii> getPersonalTransfuzii();
}
