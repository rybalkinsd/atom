package ru.atom.lecture08.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


@SpringBootApplication
public class EventServer {
    public static void main(String[] args) {
        SpringApplication.run(EventServer.class, args);
    }

    @Bean
    public Map<String, String> setUsersOnlineBean() {
        Map<String, String> users = new ConcurrentHashMap<>();
        users.put("admin", "hsl(0,100%,50%)");
        return users;
    }


    @Bean
    public List<WebSocketConfiguration> sessions() {
        return  new CopyOnWriteArrayList<>();
    }
}
