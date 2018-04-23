package gs;

import gs.replicator.Replicator;
import gs.network.Broker;
import gs.network.ConnectionPool;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class GameHandler extends TextWebSocketHandler implements WebSocketHandler {

    private GameSession gs = new GameSession(4);
    private GameMechanics gameMechanics = new GameMechanics();//TODO удалить после реализации Tick
    private final long tickTime = 5;
    private final ConnectionPool connectionPool;

    public GameHandler() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Socket Connected: " + session);
        String str = session.getUri().toString();
        //System.out.println(str.substring(str.indexOf("gameId")+7,str.indexOf("&")));
        //System.out.println(str.substring(str.indexOf("&")+6));
        ConnectionPool cp = ConnectionPool.getInstance();
        //TODO затычка на создание сесси. Создавать сессию в matchmaker?
        cp.add(session, "qwerty");

        //gs.Test();
        //Broker.getInstance().send(connectionPool.getPlayer(session), Topic.REPLICA, gs.jsonStringWalls());
        //Broker.getInstance().send(gs);

        gs.initCanvas();
        gs.setSession(session);
        gameMechanics.writeReplica(gs, cp);
        //TODO вызывать не здесь, а после тика?
        gameMechanics.read();
        /*Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                for (; ; ) {
                    try {
                        Thread.sleep(tickTime);
                        gs.tick(tickTime);
                        replicator.writeReplica(gs,cp);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        thread.start();*/
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // System.out.println("Received " + message.toString());
        //System.out.println(message.getPayload());
        Broker broker = new Broker();
        broker.receive(session, message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        gameMechanics.clear();
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }

}
