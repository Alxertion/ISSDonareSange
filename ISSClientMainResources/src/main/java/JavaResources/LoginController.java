package JavaResources;

import JavaResources.Controller;
import JavaResources.Service.Service;
import JavaResources.View.FXMLEnum;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import model.Cont;
import services.IObserver;
import services.IServices;
import services.ServiceException;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginController extends UnicastRemoteObject implements Controller,Serializable {
    private StageManager stageManager;
    private IServices service;
    private Loader loader;
    private Controller ctrl;

    @FXML
    TextField  userField;
    @FXML
    PasswordField passField;

    public LoginController() throws RemoteException {
    }

    @Override
    public void initialize(StageManager stageManager, IServices services, Loader loader) {
        this.stageManager=stageManager;
        this.service=services;
        this.loader=loader;
    }

    @FXML
    public void login(){
        try{
            FXMLLoader loaderFXML = new FXMLLoader();
            if(stageManager.getTitle().equals("LoginTransfuzii")) {
                loaderFXML.setLocation(getClass().getResource(FXMLEnum.MainViewPersonalTransfuzii.getFxmlFile()));
                Parent rootNode = loaderFXML.load();
                ctrl = loaderFXML.getController();
                stageManager.switchScene(FXMLEnum.MainViewPersonalTransfuzii, rootNode, ctrl, loader);
            }else if(stageManager.getTitle().equals("LoginDonator")){
                loaderFXML.setLocation(getClass().getResource(FXMLEnum.MainWindowDonator.getFxmlFile()));
                Parent rootNode = loaderFXML.load();
                ctrl = loaderFXML.getController();
                stageManager.switchScene(FXMLEnum.MainWindowDonator, rootNode, ctrl, loader);
            }
            service.login(new Cont("bleg"),(IObserver) ctrl);
        }catch (IOException e){
            e.printStackTrace();

        }catch (ServiceException se){
        //to do show messagebox;
        System.out.println(se.getMessage());
        }

    }
}
