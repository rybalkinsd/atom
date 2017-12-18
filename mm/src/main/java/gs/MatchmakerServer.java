package gs;

import gs.connection.MatchMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class MatchmakerServer {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MatchmakerServer.class, args);
        context.getBean(Config.class).matchMaker().start();
    }

    @Configuration
    static class Config {
        @Bean
        MatchMaker matchMaker() {
            return new MatchMaker();
        }
    }
}
