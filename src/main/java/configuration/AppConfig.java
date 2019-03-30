package configuration;

import controller.BoardController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import persistence.BoardRepository;
import persistence.HibernateUtil;
import service.Application;
import service.BoardService;
import service.MoveService;

@Configuration
public class AppConfig {
    @Bean
    public BoardRepository gameRepository(){
        return new BoardRepository();
    }

    @Bean
    public BoardService gameService(){
        return new BoardService();
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
    public BoardController gameController(){
        return new BoardController();
    }

    @Bean
    public MoveService moveService(){
        return new MoveService();
    }
}
