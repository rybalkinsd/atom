package ru.atom.mm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MatchMakerApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MatchMakerApp.class, args);
    }

}
