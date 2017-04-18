package ru.atom.lecture08.websocket;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class EventServer {
  public static void main(String[] args) {
    Server server = new Server();
    ServerConnector connector = new ServerConnector(server);
    connector.setPort(8090);
    server.addConnector(connector);

    // Setup the basic application "context" for this application at "/"
    // This is also known as the handler tree (in jetty speak)
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    // Add a websocket to a specific path spec
    ServletHolder holderEvents = new ServletHolder("ws-events", EventServlet.class);
    context.addServlet(holderEvents, "/events/*");

    try {
      server.start();
      server.dump(System.err);
      server.join();
    } catch (Throwable t) {
      t.printStackTrace(System.err);
    }
  }
}
