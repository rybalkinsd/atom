package ru.atom.boot.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringBoot {

    public static void my(String[] args) {
        SpringApplication.run(HelloSpringBoot.class, args);
    }

}
