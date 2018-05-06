package gui;

import JavaResources.Controller;
import JavaResources.Service.Service;
import JavaResources.View.FXMLEnum;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Cont;
import model.Medic;
import model.PersonalTransfuzii;
import model.Spital;
import services.IObserver;
import services.IServices;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class AdministratorController extends UnicastRemoteObject implements Controller, IObserver,Serializable {
    private StageManager stageManager;
    private IServices service;
    private Loader loader;
    private ObservableList<Medic> modelMedici;
    private ObservableList<PersonalTransfuzii> modelPersonal;
    private ObservableList<Spital> modelSpitale;
    //private ObservableList<CentruTransfuzii> modelCentreTransfuzii;
    private Cont user;


    @FXML
    private TableView<Medic> medicTableView;
    @FXML
    private TableView<PersonalTransfuzii> personalTransfuziiTableView;
    @FXML
    private TableView<Spital> spitalTableView;

    public void setService(IServices service){
        this.service=service;
        modelMedici= FXCollections.observableArrayList((ArrayList<Medic>)service.getMedici());
        medicTableView.setItems(modelMedici);
    }

    public AdministratorController() throws RemoteException {
    }

    @Override
    public void initialize(StageManager stageManager, IServices service, Loader loader) {
        this.stageManager=stageManager;
        this.loader=loader;
        this.service=service;
    }

    @Override
    public void prepareWindow() {

    }

    @Override
    public void setUser(Cont user) {
        this.user=user;
    }

    @Override
    public void notifyClient() throws RemoteException {
        System.out.println("Am fost notificat -> Administrator");
    }

    @FXML
    public void logOut(ActionEvent actionEvent) {
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.LoginWindowAdministrator.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            service.logout(this.user);
            stageManager.switchScene(FXMLEnum.LoginWindowAdministrator, rootNode, loaderFXML.getController(), loader);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
