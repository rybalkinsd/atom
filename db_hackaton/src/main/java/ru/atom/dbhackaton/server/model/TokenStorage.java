package ru.atom.dbhackaton.server.model;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentHashSet;
import ru.atom.server.AuthResources;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dmitriy on 26.03.17.
 */
public class TokenStorage {
    private static final Logger log = LogManager.getLogger(TokenStorage.class);
    private static ConcurrentHashMap<Token, User> instance = new ConcurrentHashMap<>();
    private static ConcurrentHashSet<String> logins = new ConcurrentHashSet<>();

    public static ConcurrentHashMap<Token, User> getInstance() {
        return instance;
    }

    public static User getByToken(Token token) {
        return instance.get(token);
    }

    public static boolean insert(Token token, User user) {
        if (!logins.contains(user.getName())) {
            instance.put(token, user);
            logins.add(user.getName());
            log.info("Added user :" + user.toString());
            return true;
        } else {
            log.info("User " + user.toString() + " already exist");
            return false;
        }
    }

    public static boolean remove(Token token) {
        logins.remove(getByToken(token).getName());
        instance.remove(token);
        return true;
    }

    public static boolean validate(Token token, User user) {
        try {
            if (!TokenStorage.getInstance().containsKey(token)) {
                log.info("Such token doesn't exist");
                return false;
            }

            Object username = Jwts.parser().setSigningKey(Token.key)
                    .parseClaimsJws(token.toString()).getBody().get("user");

            Object password = Jwts.parser().setSigningKey(Token.key)
                    .parseClaimsJws(token.toString()).getBody().get("password");

            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                log.info("Token " + " belongs user " + user.toString());
                return true;
            }

        } catch (JwtException | ClassCastException e) {
            log.info("Token isn't correct");
            return false;
        }
        return false;
    }


}
