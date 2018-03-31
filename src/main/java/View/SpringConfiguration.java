package View;

import Service.Service;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

public class SpringConfiguration {


    @Bean
    @Lazy(value = true) //Stage only created after Spring context bootstap
    public StageManager stageManager(Stage stage, Service service) throws IOException {
        return new StageManager(stage, service);
    }

    @Bean
    public Service service(){
        return new Service();
    }

    @Bean
    public Loader loader(){
        return new Loader();
    }
}
