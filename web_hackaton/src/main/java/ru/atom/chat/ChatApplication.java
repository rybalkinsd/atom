package ru.atom.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@SpringBootApplication
public class ChatApplication {
    public static void main(String[] args) {
        try {
            Files.write(Paths.get("./src/main/resources/history.txt"),"".getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            System.out.println(e);
        }
        SpringApplication.run(ChatApplication.class, args);
    }
}
