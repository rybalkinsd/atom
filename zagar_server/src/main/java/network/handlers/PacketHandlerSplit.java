package network.handlers;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.EjectMassMsg;
import messageSystem.messages.SplitMsg;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandEjectMass;
import protocol.CommandSplit;
import utils.JSONDeserializationException;
import utils.JSONHelper;

public class PacketHandlerSplit {
  public PacketHandlerSplit(@NotNull Session session, @NotNull String json) {
    CommandSplit commandSplit;
    try {
      commandSplit = JSONHelper.fromJSON(json, CommandSplit.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    Message message = new SplitMsg(ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress(), commandSplit);
    messageSystem.sendMessage(message);
  }
}
