package ru.atom.mm;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.atom.mm.service.ConnectionProducer;
import ru.atom.mm.service.ConnectionQueue;
import ru.atom.mm.service.GameRepository;

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
}
