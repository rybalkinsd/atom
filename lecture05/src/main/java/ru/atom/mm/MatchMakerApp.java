package ru.atom.mm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Date;


@SpringBootApplication
public class MatchMakerApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MatchMakerApp.class, args);
    }

    @Bean
    @Scope("prototype")
    Date time() {
        return new Date();
    }

    @Bean
    @Scope("prototype")
    Date timeFirst() {
        return new Date(0);
    }
}
