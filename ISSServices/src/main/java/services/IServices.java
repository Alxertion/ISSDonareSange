package services;

import model.Cont;


public interface IServices {
    void login(Cont user, IObserver client) throws ServiceException;
}
