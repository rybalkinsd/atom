package gs;

import gs.geometry.Point;
import gs.inputqueue.InputQueue;
import gs.message.Message;
import gs.message.Topic;
import gs.model.Bomb;
import gs.model.Explosion;
import gs.model.Wall;
import gs.network.ConnectionPool;
import gs.replicator.Replicator;
import gs.tick.Tickable;
import gs.util.JsonHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GameMechanics implements Tickable {
    private static final Logger log = LogManager.getLogger(GameMechanics.class);
    private static final int TIMEOUT = 10;
    Replicator replicator = new Replicator();

    public void read() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("Started");
                //TODO Заменить на Tick

                while (InputQueue.getQueue().isEmpty()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log.warn("Timeout reached");
                    }
                }
                while (!InputQueue.getQueue().isEmpty()) {
                    Message msg = InputQueue.getQueue().poll();
                    if (msg != null) {
                        if (msg.getTopic().equals(Topic.MOVE)) {
                            handleMove(msg);
                        } else if (msg.getTopic().equals(Topic.PLANT_BOMB)) {
                            handleBomb();
                            System.out.println("i am moving! " + msg);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    public void clear() {
        while (!InputQueue.getQueue().isEmpty()) {
            InputQueue.getQueue().poll();
        }
    }

    //TODO двигать игрока в указаном направлении
    public void handleMove(Message msg) {
        System.out.println("i am moving! " + msg);
    }

    //TODO создавать бомбу по координатам игрока
    public void handleBomb() {
        System.out.println("i am bombing! ");
    }

    //TODO дописать?
    public void writeReplica(GameSession gs, ConnectionPool cp) {
        replicator.writeReplica(gs, cp);
    }

    @Override
    public void tick(long elapsed) {

    }
}
