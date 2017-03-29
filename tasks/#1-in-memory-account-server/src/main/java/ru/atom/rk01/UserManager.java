package ru.atom.rk01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dmbragin on 3/28/17.
 */
public class UserManager {
    private static UserManager instance = null;
    private Map<Token, User> tokenUserMap = new ConcurrentHashMap<>();
    private List<User> users = Collections.synchronizedList(new ArrayList<User>());


    public List<User> getAll() {
        return users;
    }

    public boolean register(User user) {
        for (User oldUser : users) {
            if (oldUser.getLogin().equals(user.getLogin())) {
                return false;
            }
        }
        users.add(user);
        return true;

    }

    public Token login(User user) {
        for (User loginedUser : users) {
            if (user.equals(loginedUser)) {
                for (Map.Entry<Token, User> entry : tokenUserMap.entrySet()) {
                    if (entry.getValue().equals(user)) {
                        return entry.getKey();
                    }
                }
                Token token = new AuthToken(user.getLogin());
                tokenUserMap.put(token, user);
                return token;
            }
        }
        return null;
    }

    public boolean logout(Token token) {

        if (tokenUserMap.containsKey(token)) {
            tokenUserMap.remove(token);
            return true;
        }
        return false;
    }


    public List<User> getLoginedUsers() {
        List tmp = new ArrayList<User>(tokenUserMap.values());
        return tmp;
    }

    public User getUserByToken(Token token) {
        return tokenUserMap.get(token);
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public static void clear() {
        instance = new UserManager();
    }

    private UserManager() {}
}
