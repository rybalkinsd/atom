package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.Field;
import model.Food;
import model.Player;
import model.Virus;
import network.ClientConnectionServer;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Alex on 27.11.2016.
 */
public class EjectVirusMsg extends Message {
    @NotNull
    private final Player player;
    private final Field field;

    public EjectVirusMsg(@NotNull Player player, Field field) {
        super(ApplicationContext.instance().get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress(), ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        this.player = player;
        this.field = field;
    }


    @Override
    public void exec(Abonent abonent) {
      Virus newVirus = player.ejectVirus();
      if (newVirus != null){
          field.addVirus(newVirus);
      }
    }
}
