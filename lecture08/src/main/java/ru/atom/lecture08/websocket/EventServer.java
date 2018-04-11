package ru.atom.lecture08.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@SpringBootApplication
public class EventServer {
    public static void main(String[] args) {
        SpringApplication.run(EventServer.class, args);
    }

    @Bean
    public Map<String, String> setUsersOnlineBean() {
        Map<String,String> users = new ConcurrentHashMap<>();
        users.put("admin","hsl(0,100%,50%)");
        return users;
    }

}
