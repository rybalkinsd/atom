package ru.atom.persons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

/**
 * Created by BBPax on 23.03.17.
 */
public class TokenStorage {
    private static final Logger log = LogManager.getLogger(TokenStorage.class);
    public static Map<Token, User> logined = new HashMap<>();

    public static Token getToken(User user) throws NullPointerException {
        if (logined.containsValue(user)) {
            log.info("user {} is already logined", user.getUserName());
            return logined.keySet()
                    .stream()
                    .filter(p -> user.equals(logined.get(p)))
                    .findFirst().get();
        }
        Token temp = new Token();
        while (logined.containsKey(temp)) {
            temp.changeValue();
        }
        log.info("token: {} is associated with user : {}", temp, user.getUserName());
        logined.put(temp, user);
        return temp;
    }

    public static List<User> getOnlineUsers() {
        List<User> onlineList = new LinkedList<>();
        for (Token temp : logined.keySet()) {
            onlineList.add(logined.get(temp));
        }
        return onlineList;
    }

    @Deprecated
    public static String getOnlineList() {
        String out = "{\"users\" : [";
        for (Token temp : logined.keySet()) {
            out += logined.get(temp).getUserName() + ", ";
        }
        return out.substring(0,out.length() - 2) + "]}";
    }

    public static boolean hasToken(Token token) {
        return logined.containsKey(token);
    }

    public static User findByToken(Token token) {
        return logined.get(token);
    }

    public static void removeToken(Token token) {
        log.info("user with {} token removed",token);
        logined.remove(token);
    }
}
