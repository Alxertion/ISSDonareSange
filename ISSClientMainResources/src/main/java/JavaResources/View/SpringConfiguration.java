package JavaResources.View;

import JavaResources.Service.Service;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import services.IServices;

import java.io.IOException;

public class SpringConfiguration {


    @Bean
    @Lazy(value = true) //Stage only created after Spring context bootstap
    public StageManager stageManager(Stage stage, IServices service) throws IOException {
        return new StageManager(stage,service);
    }

    @Bean
    public Loader loader(){
        return new Loader();
    }
}
