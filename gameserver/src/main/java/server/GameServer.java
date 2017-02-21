package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jetbrains.annotations.NotNull;
import server.auth.AuthenticationFilter;

public class GameServer {

    @NotNull
    private static final Logger log = LogManager.getLogger(Server.class);

    public static void main(String[] args) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        org.eclipse.jetty.server.Server jettyServer = new org.eclipse.jetty.server.Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "server"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthenticationFilter.class.getCanonicalName()
        );

        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn(e);
            }
        } finally {
            jettyServer.destroy();
        }
    }
}
