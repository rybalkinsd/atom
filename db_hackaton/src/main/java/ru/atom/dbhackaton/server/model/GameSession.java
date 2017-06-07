package ru.atom.dbhackaton.server.model;

import java.io.Serializable;


/**
 * Created by pavel on 15.04.17.
 */
public class GameSession implements Serializable {

    private Long id;
    public static final int PLAYERS_IN_GAME = 4;

    public GameSession() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
