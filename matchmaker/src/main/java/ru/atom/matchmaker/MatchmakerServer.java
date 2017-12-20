package ru.atom.matchmaker;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Alexandr on 25.11.2017.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class MatchmakerServer {
    public static void main(String[] args) {
        SpringApplication.run(MatchmakerServer.class, args);
    }
}
