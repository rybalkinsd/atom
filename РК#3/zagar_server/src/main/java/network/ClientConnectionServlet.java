package network;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("serial")
public class ClientConnectionServlet extends WebSocketServlet {
  @Override
  public void configure(@NotNull WebSocketServletFactory factory) {
    factory.register(ClientConnectionHandler.class);
  }
}
