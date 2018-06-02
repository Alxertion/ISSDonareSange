package gui;

import JavaResources.Controller;
import JavaResources.Service.Service;
import JavaResources.View.FXMLEnum;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import model.Cont;
import services.IServices;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConditiiDonareController extends UnicastRemoteObject implements Controller,Serializable {
    private Cont user;
    private StageManager stageManager;
    private IServices service;
    private Loader loader;

    @FXML
    private CheckBox acordCheckBox;

    @FXML
    private Button forwardButton;

    @FXML
    private TextArea conditiiDonareTextArea;

    @FXML
    private Button backButton;

    public ConditiiDonareController() throws RemoteException {
    }

    @Override
    public void initialize(StageManager stageManager, IServices service, Loader loader) {
        this.stageManager = stageManager;
        this.service = service;
        this.loader = loader;
    }

    @Override
    public void prepareWindow() {

        setImagesForButtons();
        addListenerToAcordCheckBox();
        forwardButton.setVisible(false);
    }

    private void addListenerToAcordCheckBox(){
        acordCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(acordCheckBox.isSelected()){
                    forwardButton.setVisible(true);
                }
                else{
                    forwardButton.setVisible(false);
                }

            }
        });
    }

    @Override
    public void setUser(Cont user) {
        this.user=user;
    }

    private void setImagesForButtons() {
        setImageToBack();
        setImageToForward();
    }

    private void setImageToForward() {
        forwardButton.graphicProperty().bind(Bindings.when(forwardButton.hoverProperty())
                .then(new ImageView(loader.forward_button_touched()))
                .otherwise(new ImageView(loader.forward_button_untouched())));

        forwardButton.setShape(new Circle());
    }

    private void setImageToBack() {

        backButton.graphicProperty().bind(Bindings.when(backButton.hoverProperty())
                .then(new ImageView(loader.back_button_touched()))
                .otherwise(new ImageView(loader.back_button_untouched())));

        backButton.setShape(new Circle());
    }

    @FXML
    private void backButtonPressed(ActionEvent event) {

        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.MainWindowDonator.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            Controller controller = loaderFXML.getController();
            controller.setUser(user);
            stageManager.switchScene(FXMLEnum.MainWindowDonator, rootNode, loaderFXML.getController(), loader);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void forwardButtonPressed(ActionEvent event) {

        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.FormularDonator.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            Controller controller = loaderFXML.getController();
            controller.setUser(user);
            stageManager.switchScene(FXMLEnum.FormularDonator, rootNode, loaderFXML.getController(), loader);

        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
