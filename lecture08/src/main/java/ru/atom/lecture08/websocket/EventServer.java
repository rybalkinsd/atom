package ru.atom.lecture08.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"ru.atom.lecture08.websocket.dao"})
public class EventServer {
    public static void main(String[] args) {
        SpringApplication.run(EventServer.class, args);
    }
}