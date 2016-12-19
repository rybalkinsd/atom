package messageSystem.messages;

import main.ApplicationContext;
import messageSystem.Abonent;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.Player;
import network.Kicker;
import org.jetbrains.annotations.NotNull;

/**
 * Created by eugene on 12/3/16.
 */
public class KickMessage extends Message {
    @NotNull private final Player player;

    public KickMessage(Player player) {
        super(null, ApplicationContext.instance().get(MessageSystem.class).getService(Kicker.class).getAddress());
        this.player = player;
    }

    @Override
    public void exec(Abonent abonent) {
        ((Kicker) abonent).kick(player);
    }
}
