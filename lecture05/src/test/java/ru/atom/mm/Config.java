package ru.atom.mm;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import ru.atom.mm.controller.ConnectionController;
import ru.atom.mm.controller.GameController;
import ru.atom.mm.service.ConnectionProducer;
import ru.atom.mm.service.ConnectionQueue;
import ru.atom.mm.service.GameRepository;
import ru.atom.mm.service.MatchMaker;

@TestConfiguration
public class Config {
    @Bean
    public ConnectionQueue connectionQueue() {
        return new ConnectionQueue();
    }

    @Bean
    public ConnectionProducer connectionProducer() {
        return new ConnectionProducer();
    }

    @Bean
    public GameRepository gameRepository() {
        return new GameRepository();
    }

    @Bean
    public MatchMaker matchMaker() {
        return new MatchMaker();
    }

    @Bean
    @Scope("prototype")
    public ConnectionController connectionController(){
        return new ConnectionController();
    }

    @Bean
    @Scope("prototype")
    public GameController gameController(){
        return new GameController();
    }
}
