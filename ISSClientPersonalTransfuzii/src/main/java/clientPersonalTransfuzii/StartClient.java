package clientPersonalTransfuzii;

import JavaResources.Service.Service;
import JavaResources.View.FXMLEnum;
import JavaResources.View.Loader;
import JavaResources.View.SpringConfiguration;
import JavaResources.View.StageManager;
import clientPersonalTransfuzii.gui.PersonalTransfuziiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IServices;

import java.io.IOException;

public class StartClient extends Application{
    protected StageManager stageManager = null;
    protected ApplicationContext factoryWindow = null;
    protected Loader loader = null;
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        factoryWindow = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            primaryStage.setResizable(false);
            loader = factoryWindow.getBean(Loader.class);

            ApplicationContext factoryProxy = new ClassPathXmlApplicationContext("classpath:client-spring.xml");
            IServices server=(IServices)factoryProxy.getBean("service");
            System.out.println("Obtained a reference to remote server");
            stageManager = factoryWindow.getBean(StageManager.class, primaryStage,server);

            try{
                FXMLLoader loaderFXML = new FXMLLoader();
                loaderFXML.setLocation(getClass().getResource(FXMLEnum.LoginWindowPersonalTransfuzii.getFxmlFile()));
                Parent rootNode = loaderFXML.load();
                stageManager.switchScene(FXMLEnum.LoginWindowPersonalTransfuzii, rootNode, loaderFXML.getController(), loader);
            }catch (IOException e){
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("ISS Initialization  exception:"+e);
            e.printStackTrace();
        }
    }
}
