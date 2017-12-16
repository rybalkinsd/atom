package gamemechanic;

import client.Message;
import com.google.gson.Gson;
import java.util.ArrayList;

public class Replicator {
    private static final Gson gson = new Gson();

    public static String toJson(ArrayList<Object> objects) {
        return gson.toJson(objects);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static Message toMessage(String json) {
        return gson.fromJson(json, Message.class);
    }
}
