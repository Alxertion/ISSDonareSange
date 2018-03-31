package Controllers;

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
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class ConditiiDonareController implements Controller {

    private StageManager stageManager;
    private Service service;
    private Loader loader;

    @FXML
    private Button forwardButton;

    @FXML
    private TextArea conditiiDonareTextArea;

    @FXML
    private Button backButton;

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
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.MainWindowDonator.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            stageManager.switchScene(FXMLEnum.MainWindowDonator, rootNode, loaderFXML.getController(), loader);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
