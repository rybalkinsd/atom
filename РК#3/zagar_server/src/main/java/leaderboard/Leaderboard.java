package leaderboard;

import main.ApplicationContext;
import main.Service;

import messageSystem.MessageSystem;
import model.Cell;
import model.Player;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import protocol.CommandLeaderBoard;
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
public class Leaderboard extends Service implements Tickable {
    @NotNull
    private final static Logger log = LogManager.getLogger(Leaderboard.class);
    String file;

    public Leaderboard(PropertiesReader preader) {
        super("leaderboard");
        file=preader.getStringProperty("leaderboard");
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

        log.info("Updating leaderboard");
        ClientConnections clientConnections = ApplicationContext.instance().get(ClientConnections.class);
        List<Player> players = new LinkedList<>();
        for (Map.Entry<Player, Session> connection : clientConnections.getConnections()) {
            players.add(connection.getKey());
        }
        players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                Integer score1=0,score2=0;
                for(Cell cell:o2.getCells())
                {
                    score2+=cell.getMass();
                }
                for(Cell cell:o1.getCells())
                {
                    score1+=cell.getMass();
                }
                return score2.compareTo(score1);
            }
        });
        int size=Integer.min(10,players.size());
        String[] best = new String[size];
        for (int i =0;i<size;i++)
            best[i]=new String(players.get(i).getName());

        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            writer.print(JSONHelper.toJSON(best));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ApplicationContext.instance().get(MessageSystem.class).execForService(this);
    }
}