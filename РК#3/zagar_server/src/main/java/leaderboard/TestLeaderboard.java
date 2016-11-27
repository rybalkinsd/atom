package leaderboard;

import main.ApplicationContext;
import main.Service;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.SendLeaderboardMsg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ticker.Tickable;
import ticker.Ticker;
import utils.JSONHelper;
import utils.PropertiesReader;

import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by svuatoslav on 11/28/16.
 */
public class TestLeaderboard extends Service implements Tickable {
    @NotNull
    private final static Logger log = LogManager.getLogger(Leaderboard.class);
    String file;

    public TestLeaderboard(PropertiesReader preader) {
        super("leaderboard");
        file=preader.getStringProperty("leaderboard");
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            writer.print(JSONHelper.toJSON(new String[]{"1", "2", "test", "dratuti"}));
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

        ApplicationContext.instance().get(MessageSystem.class).execForService(this);
    }
}