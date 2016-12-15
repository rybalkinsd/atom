package network.handlers;

import main.ApplicationContext;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.SplitMsg;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
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
    @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    Message message = new SplitMsg(new Address("client"), commandSplit);
    messageSystem.sendMessage(message);
  }
}
