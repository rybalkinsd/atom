package ru.atom.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@SpringBootApplication
public class ChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Bean
    public Map<String, String> setUsersOnlineBean() {
        Map<String,String> users = new ConcurrentHashMap<>();
        users.put("admin","hsl(0,100%,50%)");
        return users;
    }

    @Bean
    public Pattern setUrlPattern() {
        return Pattern.compile("@^(http\\:\\/\\/|https\\:\\/\\/)?([a-z0-9][a-z0-9\\-]*\\.)+[a-z0-9][a-z0-9\\-]*$@i");
    }
}
