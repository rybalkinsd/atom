package mm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("mm/playerdb")
@ComponentScan("mm")
@EnableAutoConfiguration
@EnableScheduling
public class MatchMakerApp {
    public static void main(String[] args) {
        SpringApplication.run(MatchMakerApp.class, args);
    }
}