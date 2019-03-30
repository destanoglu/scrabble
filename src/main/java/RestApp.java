import configuration.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.Application;

import java.io.IOException;

public class RestApp
{
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        Application app = ctx.getBean(Application.class);

        try {
            app.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
