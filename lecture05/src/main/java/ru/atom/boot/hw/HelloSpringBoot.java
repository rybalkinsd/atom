package ru.atom.boot.hw;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringBoot {
    private static final Logger log = LogManager.getLogger(HelloSpringBoot.class);

    public static void main(String[] args) {
        log.info("HELLO MF");
        SpringApplication.run(HelloSpringBoot.class, args);
    }

}
