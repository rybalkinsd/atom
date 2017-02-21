package matchmaker;

import model.Field;
import model.GameSession;
import model.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by svuatoslav on 10/12/16.
 */
public class SinglePlayerSession implements GameSession {
    private Field field = new Field();
    private Player player;
    private boolean initialized=false;

    private void init()
    {
        field.init();
        initialized=true;
    }

    @Override
    public void join(@NotNull Player player) {
        this.player = player;
        if(!initialized)field.init();
        field.addPlayer(this.player);
    }

    @Override
    public String toString()
    {
        return new String("SinglePlayerSession{"+((player==null)?(""):("Player='" + player.getName()+ '\''))+"}");
    }
}