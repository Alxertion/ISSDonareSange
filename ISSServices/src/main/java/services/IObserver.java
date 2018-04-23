package services;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote{
    //orice metoda arunca RemoteException!!!!!!!
    //to do
    void notifyClient() throws RemoteException;
}

