package JavaResources;

import JavaResources.Controller;
import JavaResources.Service.Service;
import JavaResources.View.FXMLEnum;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Cont;
import services.IObserver;
import services.IServices;
import services.ServiceException;

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
    private TextField userField;
    @FXML
    private PasswordField passField;

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
                ctrl.initialize(stageManager,service,loader);
                service.login(new Cont(userField.getText(),passField.getText()),(IObserver) ctrl);
                stageManager.switchScene(FXMLEnum.MainViewPersonalTransfuzii, rootNode, ctrl, loader);
            }else if(stageManager.getTitle().equals("LoginDonator")){
                loaderFXML.setLocation(getClass().getResource(FXMLEnum.MainWindowDonator.getFxmlFile()));
                Parent rootNode = loaderFXML.load();
                ctrl = loaderFXML.getController();
                ctrl.initialize(stageManager,service,loader);
                service.login(new Cont(userField.getText(),passField.getText()),(IObserver) ctrl);
                stageManager.switchScene(FXMLEnum.MainWindowDonator, rootNode, ctrl, loader);
            }else if(stageManager.getTitle().equals("LoginAdministrator")){
                loaderFXML.setLocation(getClass().getResource(FXMLEnum.MainWindowAdministrator.getFxmlFile()));
                Parent rootNode = loaderFXML.load();
                ctrl = loaderFXML.getController();
                ctrl.initialize(stageManager,service,loader);
                service.login(new Cont(userField.getText(),passField.getText()),(IObserver) ctrl);
                stageManager.switchScene(FXMLEnum.MainWindowAdministrator, rootNode, ctrl, loader);
            }
            else if(stageManager.getTitle().equals("LoginMedic")){
                loaderFXML.setLocation(getClass().getResource(FXMLEnum.MainWindowMedic.getFxmlFile()));
                Parent rootNode = loaderFXML.load();
                ctrl = loaderFXML.getController();
                ctrl.initialize(stageManager,service,loader);
                service.login(new Cont(userField.getText(),passField.getText()),(IObserver) ctrl);
                stageManager.switchScene(FXMLEnum.MainWindowMedic, rootNode, ctrl, loader);
            }
        }catch (IOException e){
            e.printStackTrace();

        }catch (ServiceException se){
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setTitle("Mesaj eroare");
            message.setContentText(se.getMessage());
            message.showAndWait();
        }

    }
}