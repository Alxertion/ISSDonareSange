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
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import services.IObserver;
import services.IServices;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainWindowDonatorController extends UnicastRemoteObject implements Controller, IObserver,Serializable {

    private StageManager stageManager;
    private IServices service;
    private Loader loader;


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
}