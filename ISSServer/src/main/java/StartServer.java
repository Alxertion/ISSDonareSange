import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartServer {

    public static void main(String[] args){
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:server-spring.xml");
        System.out.println("Waiting for clients...(Paul :D)");
    }
}
