package ru.atom.boot.mm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.atom.thread.mm.MatchMaker;


@SpringBootApplication
public class MatchMakerApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MatchMakerApp.class, args);

        Thread matchMaker = new Thread(new MatchMaker());
        matchMaker.setName("match-maker");
        matchMaker.start();
    }
}
