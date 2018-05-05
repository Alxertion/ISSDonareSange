package gui;

import JavaResources.Controller;
import JavaResources.Service.Service;
import JavaResources.View.FXMLEnum;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import model.Cont;
import model.Donator;
import services.IObserver;
import services.IServices;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainWindowDonatorController extends UnicastRemoteObject implements Controller, IObserver,Serializable {
    private Cont user;
    private StageManager stageManager;
    private IServices service;
    private Loader loader;
    private Donator donator;

    @FXML
    private Accordion analizeAccordion;

    @FXML
    private Button vreaSaDonezButton;

    @FXML
    private Button backButton;

    public MainWindowDonatorController() throws RemoteException {
    }

    @Override
    public void initialize(StageManager stageManager, IServices service, Loader loader) {
        this.stageManager = stageManager;
        this.service = service;
        this.loader = loader;

        setImagesForButtons();

    }

    @Override
    public void prepareWindow() {

        System.out.println(user.getUsername() );
        setIdDonator();

    }

    private void setIdDonator() {

        donator = service.findDonatorByUsername(user.getUsername());
        System.out.println(donator.getIdDonator());
        System.out.println(donator.getCont().getUsername());

    }

    @Override
    public void setUser(Cont user) {
        this.user=user;
    }

    private void setImagesForButtons() {
        setImageToBack();
        setImageToVreauSaDonez();
    }

    private void setImageToVreauSaDonez() {
        vreaSaDonezButton.graphicProperty().bind(Bindings.when(vreaSaDonezButton.hoverProperty())
                .then(new ImageView(loader.vreau_sa_donez_touched()))
                .otherwise(new ImageView(loader.vreau_sa_donez_untouched())));

        vreaSaDonezButton.setShape(new Circle());
    }

    private void setImageToBack() {

        backButton.graphicProperty().bind(Bindings.when(backButton.hoverProperty())
                .then(new ImageView(loader.back_button_touched()))
                .otherwise(new ImageView(loader.back_button_untouched())));

        backButton.setShape(new Circle());
    }

    @FXML
    private void vreauSaDonezButtonPressed(ActionEvent event) {

        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.ConditiiDonare.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            stageManager.switchScene(FXMLEnum.ConditiiDonare, rootNode, loaderFXML.getController(), loader);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void notifyClient() throws RemoteException {
        System.out.println("Am fost notificat -> Donator");
    }

    @FXML
    public void logOut(ActionEvent actionEvent) {
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.LoginWindowDonator.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            service.logout(this.user);
            stageManager.switchScene(FXMLEnum.LoginWindowDonator, rootNode, loaderFXML.getController(), loader);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
