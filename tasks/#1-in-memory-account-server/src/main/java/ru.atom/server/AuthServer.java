package ru.atom.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by konstantin on 25.03.17.
 */
public class AuthServer {
    private static Logger logger = LogManager.getLogger(AuthServer.class);

    private static Server jettyServer;

    public static void serverStart() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.server"
        );
        jettyServer.start();
    }

    public static void serverStop() throws Exception {
        logger.info("Server is stoped");
        jettyServer.stop();
    }

    public static void main(String[] args) throws Exception {
        logger.info("Server is start");
        serverStart();
    }
}
