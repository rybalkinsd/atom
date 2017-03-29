package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import ru.atom.resources.AuthFilter;

import java.net.BindException;


public class AuthServer {
    private static final Logger logger = LogManager.getLogger(AuthServer.class);
    private static Server jettyServer = new Server(8080);

    public static void start() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthFilter.class.getCanonicalName()
        );

        jettyServer.start();
        logger.info("Сервер успешно запущен");
    }

    public static void stop() throws Exception {
        if (jettyServer.isStarted()) {
            jettyServer.stop();
            logger.info("Сервер остановлен");
            return;
        }
        logger.info("Что-то пошло не так, сервер не был остановлен");
    }

    public static void restart() throws Exception {
        if (jettyServer.isStarted()) {
            jettyServer.stop();
            jettyServer.start();
            logger.info("Сервер перезапущен");
            return;
        } else {
            jettyServer.start();
            return;
        }
        //logger.info("Что-то пошло не так, сервер не был перезапущен");
    }

    public static void main(String[] args) throws Exception {
        start();
    }

}
