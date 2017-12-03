package ru.atom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.atom.dao.PlayerDao;
import ru.atom.dao.GameDao;


@SpringBootApplication
public class MatchMakerApp {
    public static void main(String[] args) {

        SpringApplication.run(MatchMakerApp.class, args);
    }
}
