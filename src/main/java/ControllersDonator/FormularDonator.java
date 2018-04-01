package ControllersDonator;

import Service.Service;
import View.FXMLEnum;
import View.Loader;
import View.StageManager;
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

import java.io.IOException;


public class FormularDonator implements Controller {

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
    private Service service;
    private Loader loader;


    @Override
    public void initialize(StageManager stageManager, Service service, Loader loader) {
        this.stageManager = stageManager;
        this.service = service;
        this.loader = loader;

        setImagesForButtons();
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
