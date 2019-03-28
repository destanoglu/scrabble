package configuration;

import controller.BoardController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import persistence.EmployeeRepository;
import persistence.HibernateUtil;
import service.Application;
import service.EmployeeService;

@Configuration
public class AppConfig {
    @Bean
    public EmployeeRepository employeeRepository(){
        return new EmployeeRepository();
    }

    @Bean
    public EmployeeService employeeService(){
        return new EmployeeService();
    }

    @Bean
    public HibernateUtil hibernateUtil(){
        return new HibernateUtil();
    }

    @Bean
    public Application application(){
        return new Application();
    }

    @Bean
    public BoardController boardController(){
        return new BoardController();
    }
}
