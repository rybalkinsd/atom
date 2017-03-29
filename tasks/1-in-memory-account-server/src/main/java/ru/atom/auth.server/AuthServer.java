package ru.atom.auth.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthServer {
    private static final int port = 8095;
    private static final Logger log = LogManager.getLogger(AuthServer.class);

    private static Server jettyServer = new Server(port);

    public static void serverRun() throws Exception {

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.auth.server"
        );
        jettyServer.start();
        log.info("Server successfully started on port " + port);
    }

    public static void main(String[] args) throws Exception {
        serverRun();
    }

    public static void serverStop() throws Exception {
        jettyServer.stop();
    }
}
