package configuration;

import controller.BoardController;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import persistence.BoardRepository;
import persistence.HibernateUtil;
import service.*;

import java.io.IOException;

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
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MovementService moveService(){
        return new MovementService();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MoveControl borderControl(){
        return new BorderControl();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MoveControl placementControl(){
        return new PlacementControl();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MoveControl meaningControl() throws IOException {
        return new MeaningControl();
    }

    @Bean
    public Integer boardSize(){
        return 15;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public BoardScoringService scoringSevice(){
        return new BoardScoringService();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MovementService movementService(){
        return new MovementService();
    }
}
