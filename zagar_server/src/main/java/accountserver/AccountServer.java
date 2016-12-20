package accountserver;

import accountserver.api.auth.AuthenticationFilter;
import main.ApplicationContext;
import main.Service;
import messageSystem.MessageSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;


public class AccountServer extends Service {
    private final static @NotNull Logger log = LogManager.getLogger(AccountServer.class);
    private final int port;
    private org.eclipse.jetty.server.Server server;

    public AccountServer(int port) {
        super("account_server");
        this.port = port;
        startApi();
    }

    public static void main(@NotNull String[] args) throws Exception {
        log.info("FUCK:I am in main");
        new AccountServer(8080).startApi();
    }

    private void startApi() {
        log.info("FUCK:I am in startApi()");
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        server = new org.eclipse.jetty.server.Server(port);
        server.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "accountserver.api"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthenticationFilter.class.getCanonicalName()
        );

        log.info(getName() + " started on port " + port);
        try {
            server.start();
        } catch (Exception e) {
            log.fatal(e.getMessage());
        }
    }

    @Override
    public void interrupt() {
        try {
            server.stop();
        } catch (Exception ignored) {

        } finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {
        log.info("FUCK:I am starting startApi()");
      //  startApi();
        try {
            while (true) {
                MessageSystem ms = ApplicationContext.instance().get(MessageSystem.class);
                ApplicationContext.instance().get(MessageSystem.class).execOneForService(this, 100);
            }
        } catch (InterruptedException e) {
            log.fatal(e.getMessage());
        }
    }
}
