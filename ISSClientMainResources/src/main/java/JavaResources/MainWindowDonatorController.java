//package JavaResources;
//
//import JavaResources.Service.Service;
//import JavaResources.View.FXMLEnum;
//import JavaResources.View.Loader;
//import JavaResources.View.StageManager;
//import javafx.beans.binding.Bindings;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.control.Button;
//import javafx.scene.image.ImageView;
//import javafx.scene.shape.Circle;
//
//import java.io.IOException;
//
//public class MainWindowDonatorController implements Controller {
//
//    private StageManager stageManager;
//    private Service service;
//    private Loader loader;
//
//
//    @FXML
//    private Button vreaSaDonezButton;
//
//    @FXML
//    private Button backButton;
//
//    @Override
//    public void initialize(StageManager stageManager, Service service, Loader loader) {
//        this.stageManager = stageManager;
//        this.service = service;
//        this.loader = loader;
//
//        setImagesForButtons();
//
//
//    }
//
//    private void setImagesForButtons() {
//        setImageToBack();
//        setImageToVreauSaDonez();
//    }
//
//    private void setImageToVreauSaDonez() {
//        vreaSaDonezButton.graphicProperty().bind(Bindings.when(vreaSaDonezButton.hoverProperty())
//                .then(new ImageView(loader.vreau_sa_donez_touched()))
//                .otherwise(new ImageView(loader.vreau_sa_donez_untouched())));
//
//        vreaSaDonezButton.setShape(new Circle());
//    }
//
//    private void setImageToBack() {
//
//        backButton.graphicProperty().bind(Bindings.when(backButton.hoverProperty())
//                .then(new ImageView(loader.back_button_touched()))
//                .otherwise(new ImageView(loader.back_button_untouched())));
//
//        backButton.setShape(new Circle());
//    }
//
//    @FXML
//    private void vreauSaDonezButtonPressed(ActionEvent event) {
//
//        try {
//            FXMLLoader loaderFXML = new FXMLLoader();
//            loaderFXML.setLocation(getClass().getResource(FXMLEnum.ConditiiDonare.getFxmlFile()));
//            Parent rootNode = loaderFXML.load();
//            stageManager.switchScene(FXMLEnum.ConditiiDonare, rootNode, loaderFXML.getController(), loader);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//}
