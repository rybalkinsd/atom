package ru.atom.service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import ru.atom.model.Game;
import ru.atom.model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameIdBroker implements Runnable {

    private final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    private final OkHttpClient client = new OkHttpClient();
    private final String protocol = "http://";
    private final String host = "localhost";
    private final String port = ":8090";
    private Request request;
    ArrayList<Connection> connections;
    int requiredPlayerAmount = 0;
    MatchMakerService mmService;


    public GameIdBroker(int requiredPlayerAmount, ArrayList<Connection> connections, MatchMakerService mmservice) {
        request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(protocol + host + port + "/game/create?playerCount=" + requiredPlayerAmount)
                .build();

        this.connections = connections;
        this.requiredPlayerAmount = requiredPlayerAmount;
        this.mmService = mmservice;

    }

    @Override
    public void run() {
        try {
            long gameId = Long.parseLong(client.newCall(request).execute().body().string());

            Game game = new Game().setId(gameId);
            ArrayList<Player> players = new ArrayList<>(requiredPlayerAmount);
            for (Connection connection : connections) {
                players.add(new Player().setGame(game));
            }
            game.setPlayers(players);
            mmService.saveGame(game);
            for (Connection connection : connections) {
                connection.getPlayer().setGame(game);
                synchronized (connection.getInvoker()) {
                    connection.getInvoker().notify();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
