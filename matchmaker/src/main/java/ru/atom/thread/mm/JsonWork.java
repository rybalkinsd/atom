package ru.atom.thread.mm;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class JsonWork {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(MatchMaker.class);
    private static final String FILENAME = "D:\\javahw\\hw\\atom\\matchmaker\\src\\" +
            "main\\java\\ru\\atom\\thread\\mm\\file.json";
    private static String gameId;
    private static String player1;
    private static String player2;

    public static void setToJson(String id, String player1, String player2) {
        JSONObject object = new JSONObject();
        gameId = id;
        object.put("GameId", id);
        player1 = player1;
        player2 = player2;
        object.put("Player1", player1);
        object.put("Player2", player2);

        try (FileWriter writer = new FileWriter(FILENAME, true)) {
            writer.write(object.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            log.info("Error writing to database");
        }
    }


}
