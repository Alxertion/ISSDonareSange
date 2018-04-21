package clientMedic;

import JavaResources.Service.Service;
import JavaResources.View.FXMLEnum;
import JavaResources.View.Loader;
import JavaResources.View.SpringConfiguration;
import JavaResources.View.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class StartClient extends Application {

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
        System.out.println("merge");

    }

    private void displayInitialScene(){
        try{
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.LoginWindow.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            stageManager.switchScene(FXMLEnum.LoginWindow, rootNode, loaderFXML.getController(), loader);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
