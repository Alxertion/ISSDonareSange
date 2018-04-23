package gui;

import JavaResources.Controller;
import JavaResources.Service.Service;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;
import services.IObserver;
import services.IServices;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MedicController extends UnicastRemoteObject implements Controller, IObserver,Serializable {
    private StageManager stageManager;
    private IServices service;
    private Loader loader;

    public MedicController() throws RemoteException {
    }

    @Override
    public void initialize(StageManager stageManager, IServices service, Loader loader) {
        this.stageManager=stageManager;
        this.loader=loader;
        this.service=service;
    }

    @Override
    public void notifyClient() throws RemoteException {
        System.out.println("Am fost notificat -> Medic");
    }
}
