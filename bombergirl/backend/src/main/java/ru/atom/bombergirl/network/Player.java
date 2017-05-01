package ru.atom.bombergirl.network;

import org.eclipse.jetty.websocket.api.Session;
import ru.atom.bombergirl.gamemodel.model.Action;
import ru.atom.bombergirl.gamemodel.model.Girl;

/**
 * Created by ikozin on 01.05.17.
 */
public class Player {
    private Girl pawn;
    private final Session session;

    private static int counter = 0;
    private final int id = counter++;

    public Player (Session session, Girl pawn) {
        this.session = session;
        this.pawn = pawn;
    }

    public void start(Action action) {
        pawn.addAction(action);
    }
}
