import Service.Service;
import View.FXMLEnum;
import View.Loader;
import View.SpringConfiguration;
import View.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main extends Application {

    protected StageManager stageManager = null;
    protected ApplicationContext factory = null;
    protected Loader loader = null;

    public static void main(String[] args){

        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        factory = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        primaryStage.setResizable(false);
        Service service = factory.getBean(Service.class);
        loader = factory.getBean(Loader.class);

        stageManager = factory.getBean(StageManager.class, primaryStage, service);

        displayInitialScene();
        // here the initial scene will be set to login
        //displayInitialSceneTransfusionsStaff();
        System.out.println("TestCommit");
    }

    private void displayInitialScene() {

        try{
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.MainWindowDonator.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            stageManager.switchScene(FXMLEnum.MainWindowDonator, rootNode, loaderFXML.getController(), loader);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void displayInitialSceneTransfusionsStaff(){
        try{
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.MainViewPersonalTransfuzii.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            stageManager.switchScene(FXMLEnum.MainViewPersonalTransfuzii, rootNode, loaderFXML.getController(), loader);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
