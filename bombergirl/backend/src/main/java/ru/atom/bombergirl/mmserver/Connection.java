package ru.atom.bombergirl.mmserver;

import org.eclipse.jetty.websocket.api.Session;
import ru.atom.bombergirl.gamemodel.model.Action;
import ru.atom.bombergirl.gamemodel.model.Pawn;

/**
 * Created by ikozin on 17.04.17.
 */
public class Connection {
    //private final String name;
    private final Session session;
    private Pawn pawn;
    private static int counter = 0;
    private final int id = counter++;

    public Connection(/*String name, */Session session) {
        //this.name = name;
        this.session = session;
    }

    public void start(Action action) {
        pawn.addAction(action);
    }

    public void setGirl(Pawn pawn) {
        this.pawn = pawn;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public Session getSession() {
        return session;
    }

    /*
    public String getName() {
        return name;
    }
*/

    @Override
    public String toString() {
        return "Connection{" +
                ", id='" + id + '\'' +
                '}';
    }
}
