package gameserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import threads.GameServer;


@SpringBootApplication
public class GameServerApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(GameServerApplication.class, args);
        Logger log = LogManager.getLogger(GameServer.class);
        log.info("Application Started");
        Thread gameServer = new Thread(new GameServer());
        gameServer.setName("game-server");
        gameServer.start();
    }
}
