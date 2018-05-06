package JavaResources;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import model.Cont;
import services.IServices;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class FormularDonator extends UnicastRemoteObject implements Controller,Serializable {

    @FXML
    private TextField numeTextField;

    @FXML
    private TextField prenumeTextField;

    @FXML
    private DatePicker dataNasteriiDatePicker;

    @FXML
    private TextField cnpTextField;

    @FXML
    private TextField domiciliuCITextField;

    @FXML
    private ComboBox localitateCIComboBox;

    @FXML
    private TextField resedintaTextField;

    @FXML
    private ComboBox localitateResedintaTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField telefonTextField;

    @FXML
    private TextField numePacientTextField;

    @FXML
    private TextField prenumePacientTextField;

    @FXML
    private Button backButton;

    @FXML
    private Button forwardButton;

    private StageManager stageManager;
    private IServices service;
    private Loader loader;

    public FormularDonator() throws RemoteException {
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

    }

    @Override
    public void setUser(Cont user) {

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
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.ConditiiDonare.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            stageManager.switchScene(FXMLEnum.ConditiiDonare, rootNode, loaderFXML.getController(), loader);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
