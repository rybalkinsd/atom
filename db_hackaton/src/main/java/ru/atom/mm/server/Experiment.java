package ru.atom.mm.server;

import com.google.gson.Gson;
import ru.atom.mm.infogame.Match;
import ru.atom.object.User;

/**
 * Created by Fella on 18.04.2017.
 */
public class Experiment {
    public static void main(String[] args) {
        Gson gson = new Gson();

        Match match = new Match();


        Integer id = 1;
        Integer gameId = 123;


        User user1 = new User().setLogin("Pasha");
        User user2 = new User().setLogin("Grisha");
        User user3 = new User().setLogin("Petr");
        User user4 = new User().setLogin("Gasha");


        match.putScore(user1.getLogin(), 10).putScore(user2.getLogin(), 30)
                .putScore(user3.getLogin(), 1).putScore(user4.getLogin(), 74);

        match.setGameId(gameId).setId(id);


        System.out.println(gson.toJson(match));
        String json = gson.toJson(match);
        System.out.println(gson.fromJson(json, match.getClass()).getGameId());
    }
}
