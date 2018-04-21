package JavaResources.View;

import JavaResources.Controller;
import JavaResources.Service.Service;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {

    private final Stage primaryStage;
    private final Service service;


    public StageManager(Stage primaryStage, Service service) {
        this.primaryStage = primaryStage;
        this.service = service;
    }

    public void switchScene(final FXMLEnum view, Parent rootNode, JavaResources.Controller controller, Loader loader) {
        controller.initialize(this, service, loader);
        Parent viewRootNodeHierarchy = rootNode;
        show(viewRootNodeHierarchy, view.getTitle());
    }

    private void show(final Parent rootnode, String title) {
        Scene scene = prepareScene(rootnode);

        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            System.out.println(exception.getMessage() + " ");
            exception.printStackTrace();
        }
    }

    private Scene prepareScene(Parent rootnode){
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }

}
