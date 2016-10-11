package ru.atom.servlets.examples;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public class EchoServerLauncher {
  @NotNull
  private static final Logger log = LogManager.getLogger(EchoServerLauncher.class);
  private static final int PORT = 5555;

  public static void main(@NotNull String[] args) {
    log.info("Dummy server started on port " + 5555);

    Server server = new Server(5555);//create jetty server on some port

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);//context is a map of URLS and their servers
    context.addServlet(new ServletHolder(new DummyServlet()), "/echo");//register servlet as server for requests on /dummy URL

    server.setHandler(context);
    try {
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      server.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
