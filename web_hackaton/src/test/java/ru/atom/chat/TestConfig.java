package ru.atom.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class TestConfig {
    @Bean
    public ChatController chatController() {
        return new ChatController();
    }

    @Bean
    public HistoryFile historyFile() {
        return new HistoryFile();
    }
}
