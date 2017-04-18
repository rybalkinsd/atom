package ru.atom.dbhackaton.server.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by pavel on 15.04.17.
 */
public class GameSession implements Serializable {

    public final static int PLAYERS_IN_GAME = 4;

    private Long id;

    public GameSession() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
