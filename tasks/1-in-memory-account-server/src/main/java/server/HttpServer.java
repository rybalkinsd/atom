package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpServer {
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
                "server"
        );

        //        jerseyServlet.setInitParameter(
        //                "com.sun.jersey.spi.container.ContainerRequestFilters",
        //                AuthFilter.class.getCanonicalName()
        //        );

        jettyServer.start();
    }

    public static void serverStop() throws Exception {
        jettyServer.stop();
    }

    public static void main(String[] args) throws Exception {
        serverStart();
    }
}