package leaderboard;

import javafx.util.Pair;
import main.ApplicationContext;
import main.Service;

import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.SendLeaderboardMsg;
import messageSystem.messages.UpdateLeaderboardMsg;
import model.Cell;
import model.Player;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import ticker.Tickable;
import ticker.Ticker;
import utils.JSONHelper;
import utils.PropertiesReader;

import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * Created by svuatoslav on 11/27/16.
 */
public class LeaderboardImpl extends Leaderboard implements Tickable {
    @NotNull
    private final static Logger log = LogManager.getLogger(LeaderboardImpl.class);
    String file;

    public LeaderboardImpl(PropertiesReader preader) {
        file=preader.getStringProperty("leaderboard");
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            writer.print(JSONHelper.toJSON(new String[0]));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        log.info(getAddress() + " started");
        Ticker ticker = new Ticker(this, 1);
        ticker.loop();
    }

    @Override
    public void tick(long elapsedNanos) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error(e);
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        log.info("Start leaderboard replication");
        @org.jetbrains.annotations.NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
        Message message = new SendLeaderboardMsg(this.getAddress());
        messageSystem.sendMessage(message);

        log.info("Start leaderboard update");
        message = new UpdateLeaderboardMsg(this.getAddress());
        messageSystem.sendMessage(message);

        messageSystem.execForService(this);
    }

    @Override
    public void update() {
        log.info("Updating leaderboard");
        ClientConnections clientConnections = ApplicationContext.instance().get(ClientConnections.class);
        List<Pair<String,Integer>> players = new LinkedList<>();
        for (Map.Entry<Player, Session> connection : clientConnections.getConnections()) {
            int score=0;
            Player player=connection.getKey();
            for(Cell cell:player.getCells())
            {
                score+=cell.getMass();
            }
            players.add(new Pair<>(player.getName(),score));
        }
        players.sort(new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        int size=Integer.min(10,players.size());
        String[] best = new String[size];
        for (int i =0;i<size;i++) {
            Pair<String,Integer> player=players.get(i);
            best[i] = player.getKey() + " " + player.getValue();
        }

        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            writer.print(JSONHelper.toJSON(best));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}