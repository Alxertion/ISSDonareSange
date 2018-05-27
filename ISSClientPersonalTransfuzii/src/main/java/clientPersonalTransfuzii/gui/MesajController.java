package clientPersonalTransfuzii.gui;

import JavaResources.Controller;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import model.Cont;
import model.Donator;
import model.MailEnum;
import services.FrontException;
import services.IServices;

import javax.swing.*;
import java.io.Serializable;
import java.net.URI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MesajController extends UnicastRemoteObject implements Controller,Serializable {
    IServices service;
    @FXML
    TextArea continutNotificare;
    @FXML
    RadioButton mesajTelefonicRadio,mesajEmailRadio;

    ObservableList<Donator> observableDonator;
    public MesajController() throws RemoteException {

    }
    @FXML
    public void notificareDonator(ActionEvent actionEvent){
        try{
            String continut=continutNotificare.getText();
            if(continut.equals(""))
                throw new FrontException("Nu ati introdus nicio informatie");
            if(observableDonator.size()==0)
                throw new FrontException("Nu avem donatori de notificat.");
            if(mesajTelefonicRadio.isSelected()) {
                service.sendSMS("0773302809",continut);
            }
            else{
                service.sendEmail("oti_otniel97@yahoo.com", "", continut, MailEnum.NOTIFICARE_DONATOR);
            }
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Mesaj de informare");
            message.setContentText("Notificarea a fost transmisa.");
            message.showAndWait();
        }catch (FrontException e){
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setTitle("Mesaj eroare");
            message.setContentText(e.getMessage());
            message.showAndWait();
        }

    }

    @FXML
    public void notificareDonatori(ActionEvent actionEvent){
        try{
            String continut=continutNotificare.getText();
            if(continut.equals(""))
                throw new FrontException("Nu ati introdus nicio informatie");
            if(observableDonator.size()==0)
                throw new FrontException("Nu avem donatori de notificat.");
            for(Donator donator:observableDonator){
                //service.sendEmail(donator.getEmail(),"",continut, MailEnum.NOTIFICARE_DONATOR);
                service.sendEmail("oti_otniel97@yahoo.com","",continut, MailEnum.NOTIFICARE_DONATOR);
            }

            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Mesaj de informare");
            message.setContentText("Emailul a fost transmis.");
            message.showAndWait();
        }catch (FrontException e){
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setTitle("Mesaj eroare");
            message.setContentText(e.getMessage());
            message.showAndWait();
        }
    }

    @Override
    public void initialize(StageManager stageManager, IServices services, Loader loader) {
        this.service=services;
        mesajEmailRadio.setSelected(true);
    }

    public void setObservableDonator(ObservableList donatori){
        observableDonator=donatori;
    }

    @Override
    public void prepareWindow() {

    }

    @Override
    public void setUser(Cont user) {

    }
}
