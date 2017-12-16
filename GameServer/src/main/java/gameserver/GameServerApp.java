package gameserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GameServerApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(GameServerApp.class, args);
        Thread gameServer = new Thread(new GameServer());
        gameServer.setName("GameSever-id");
        gameServer.start();
    }
}
