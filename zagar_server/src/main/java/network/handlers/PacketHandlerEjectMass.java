package network.handlers;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.EjectMassMsg;
import network.ClientConnectionServer;
import network.packets.PacketAuthFail;
import network.packets.PacketAuthOk;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuth;
import protocol.CommandEjectMass;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.io.IOException;

public class PacketHandlerEjectMass {
  public PacketHandlerEjectMass(@NotNull Session session, @NotNull String json) {
    CommandEjectMass commandEjectMass;
    try {
      commandEjectMass = JSONHelper.fromJSON(json, CommandEjectMass.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    Message message = new EjectMassMsg(ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress(), commandEjectMass);
    messageSystem.sendMessage(message);
  }
}
