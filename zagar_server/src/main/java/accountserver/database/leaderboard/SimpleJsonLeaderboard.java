package accountserver.database.leaderboard;

import accountserver.database.users.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.SortedByValueMap;
import utils.json.JSONHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xakep666 on 26.11.16.
 * <p>
 * Sends leaderboard from file to client (NOT FOR PRODUCTION!!! ONLY FOR TESTS)
 */
public class SimpleJsonLeaderboard implements LeaderboardDao {
    private static final Logger log = LogManager.getLogger(SimpleJsonLeaderboard.class);
    private static InputStream jsonFileInput = SimpleJsonLeaderboard.class
            .getClassLoader()
            .getResourceAsStream("testleaderboard.json");
    private static Map<User, Integer> users = new HashMap<>();

    static {

        try {
            if (jsonFileInput == null) {
                log.error("File testleaderboard.json not found");
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(jsonFileInput));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) out.append(line);
                String fileContent = out.toString();
                JsonObject record = JSONHelper.getJSONObject(fileContent);
                JsonArray userRecord = record.get("users").getAsJsonArray();
                JsonArray scoreRecord = record.get("scores").getAsJsonArray();
                int i = 0;
                for (JsonElement e : userRecord) {
                    users.put(new User(e.getAsString(), "something"), scoreRecord.get(i++).getAsInt());
                }
                log.info("Got users {}", users);
            }
        } catch (Exception e) {
            log.fatal(e.getMessage());
        }
    }

    @Override
    public void addUser(@NotNull User user) {

    }

    @Override
    public void removeUser(@NotNull User user) {

    }

    @Override
    public void updateScore(@NotNull User user, int scoreToAdd) {

    }

    @NotNull
    @Override
    public Map<User, Integer> getTopUsers(int count) {
        log.debug("sending top {} users {}", count, users);
        return SortedByValueMap.sortByValues(users);
    }
}
