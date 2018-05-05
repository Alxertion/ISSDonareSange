package clientPersonalTransfuzii.gui;

import JavaResources.Controller;
import JavaResources.Service.Service;
import JavaResources.View.FXMLEnum;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeBodyPart;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeMultipart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Cont;
import model.Donator;
import org.hibernate.Session;
import services.IObserver;
import services.IServices;
import sun.plugin2.message.Message;
import sun.plugin2.message.transport.Transport;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.io.IOException;
import java.io.Serializable;
import java.net.PasswordAuthentication;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class PersonalTransfuziiController extends UnicastRemoteObject implements  Controller, IObserver,Serializable {
    private StageManager stageManager;
    private IServices service;
    private Loader loader;
    private Cont user;
    private ObservableList<Donator> observableListDonatori;
    @FXML
    TabPane tabPanePersonal;
    @FXML
    ListView listaDonatori;

    public PersonalTransfuziiController() throws RemoteException {

    }


    @FXML
    public void notifyButtonPressed(ActionEvent actionEvent){
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.TrimiteMesaj.getFxmlFile()));
            AnchorPane mailView = loaderFXML.load();
            Scene scene=new Scene(mailView);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @FXML
    public void checkConditions(ActionEvent actionEvent){
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.FormularDonator2.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            tabPanePersonal.getSelectionModel().getSelectedItem().setContent(rootNode);

        }catch (IOException e){
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(StageManager stageManager,IServices service, Loader loader) {
              this.stageManager=stageManager;
              this.loader=loader;
              this.service=service;
    }

    @Override
    public void setUser(Cont user) {
        this.user=user;
    }

    @Override
    public void notifyClient() throws RemoteException {
        System.out.println("Am fost notificat -> Personal");
    }

    @FXML
    public void logOut(ActionEvent actionEvent) throws RemoteException {
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.LoginWindowPersonalTransfuzii.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            service.logout(this.user);
            stageManager.switchScene(FXMLEnum.LoginWindowPersonalTransfuzii, rootNode, loaderFXML.getController(), loader);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void prepareWindow(){
        loadListDonatori();
    }

    public void loadListDonatori(){
        List<Donator> donatori=service.getDonatori();
        observableListDonatori= FXCollections.observableArrayList(donatori);
        listaDonatori.setItems(observableListDonatori);

    }
}
